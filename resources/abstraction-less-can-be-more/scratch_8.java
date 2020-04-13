import java.util.List;

import static java.util.Arrays.asList;

class Main8 {

    public static void main(String[] args) {
        doThingsToDo();
    }

    static void doThingsToDo() {
        ThingsToDo thingsToDo = importantThingsToDo();

        // Option A
        doQuickly(thingsToDo);

        // Option B
        doItWithoutBlockingOthers(thingsToDo);
    }

    // you prefer more abstract concept - you prefer simplicity and compatibility
    private static void doQuickly(ThingToDo thingToDo) {
        // don't prepare stuff
        thingToDo.doIt();
        // don't clean up stuff

        // you decided that you don't want to know about it
        // so it doesn't even compile
        // thingToDo.things();
    }

    // you prefer less abstract concept - you prefer more control
    private static void doItWithoutBlockingOthers(ThingsToDo thingsToDo) {
        long startTime = System.currentTimeMillis();
        int thingsDone = 0;
        thingsToDo.things().forEach(thingToDo -> {
            if (startTime + 100 > System.currentTimeMillis()) {
                System.out.println(
                        "Sorry, come back later, can't block others any more, done "
                                + thingsDone + " out of " +
                                thingsToDo.things().size()
                );
            }
            doQuickly(thingsToDo);
        });
    }

    private static ThingsToDo importantThingsToDo() {
        return new ThingsToDo() {
            public void doIt() {
                things().forEach(ThingToDo::doIt);
            }

            public List<ThingToDo> things() {
                return asList(
                        () -> System.out.println("I am now doing the laundry..."),
                        () -> System.out.println("I am now walking the dog..."),
                        () -> System.out.println("I am now reading a book...")
                );
            }
        };
    }

    interface ThingsToDo extends ThingToDo {
        List<ThingToDo> things();
    }

    interface ThingToDo {
        void doIt();
    }
}