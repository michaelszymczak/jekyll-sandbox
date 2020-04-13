class Main6 {

    public static void main(String[] args) {
        doThingsToDo();
    }

    static void doThingsToDo() {
        ThingsToDo importantThingsToDo = importantThingsToDo();
        ThingsToDo funThingsToDo = funThingsToDo();


        // option A
        doProperly(importantThingsToDo);
        doQuickly(funThingsToDo);

        // option B
        doQuickly(importantThingsToDo);
        doProperly(funThingsToDo);

        // option C
        doProperly(funThingsToDo);
        doQuickly(importantThingsToDo);

        //
        // ...
    }

    private static void doProperly(ThingsToDo thingsToDo) {
        // prepare stuff
        thingsToDo.doIt();
        // clean up the stuff
    }

    private static void doQuickly(ThingsToDo thingsToDo) {
        // don't prepare stuff
        thingsToDo.doIt();
        // don't clean up stuff
    }

    private static ThingsToDo importantThingsToDo() {
        return new ThingsToDo() {
            @Override
            public void doIt() {
                System.out.println("I am now doing the laundry...");
                System.out.println("I am now walking the dog...");
                System.out.println("I am now reading a book...");
            }
        };
    }

    private static ThingsToDo funThingsToDo() {
        return new ThingsToDo() {
            @Override
            public void doIt() {
                System.out.println("I am playing tennis...");
            }
        };
    }

    interface ThingsToDo {
        void doIt();
    }
}