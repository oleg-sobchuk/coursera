package edu.coursera.concurrent;

import edu.rice.pcdp.Actor;
import edu.rice.pcdp.PCDP;

import java.util.ArrayList;
import java.util.List;

/**
 * An actor-based implementation of the Sieve of Eratosthenes.
 *
 * TODO Fill in the empty SieveActorActor actor class below and use it from
 * countPrimes to determin the number of primes <= limit.
 */
public final class SieveActor extends Sieve {

    public final List<SieveActorActor> primes = new ArrayList<>();

    /**
     * {@inheritDoc}
     *
     * TODO Use the SieveActorActor class to calculate the number of primes <=
     * limit in parallel. You might consider how you can model the Sieve of
     * Eratosthenes as a pipeline of actors, each corresponding to a single
     * prime number.
     */
    @Override
    public int countPrimes(final int limit) {
        SieveActorActor sievActor = new SieveActorActor(2);
        PCDP.finish(() -> {
            for (int i = 3; i <= limit; i += 2) {
                sievActor.send(i);
            }
            sievActor.send(0);
        });

        int count = 1;
        SieveActorActor next = sievActor.next;
        while (next != null) {
            count++;
            next = next.next;
        }
        return count;
    }

    /**
     * An actor class that helps implement the Sieve of Eratosthenes in
     * parallel.
     */
    public static final class SieveActorActor extends Actor {
        private SieveActorActor next;
        private final int myPrime;

        public SieveActorActor(int myPrime) {
            this.myPrime = myPrime;
        }

        /**
         * Process a single message sent to this actor.
         * @param msg Received message
         */
        @Override
        public void process(final Object msg) {
            if (msg instanceof Integer) {
                Integer valueToCheck = (Integer) msg;
                if (valueToCheck > 0) {
                    if (valueToCheck % myPrime != 0) {
                        if (next != null) {
                            next.send(msg);
                        } else {
                            next = new SieveActorActor(valueToCheck);
                        }
                    }
                }
            }

        }
    }
}
