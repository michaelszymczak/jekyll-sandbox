class Main6 {

    public static void main(String[] args) {
        doThingsToDo();
    }

    static void doThingsToDo() {
        // a thing
        ThingsToDo thingsToDo = thingsToDo();

        // do a thing
        doThingsProperly(thingsToDo);
    }

    private static void doThingsProperly(ThingsToDo thingsToDo) {
        // prepare stuff
        thingsToDo.doIt();
        // clean up the stuff
    }

    private static ThingsToDo thingsToDo() {
        return new ThingsToDo() {
            @Override
            public void doIt() {
                System.out.println("I am now doing the laundry...");
                System.out.println("I am now walking the dog...");
                System.out.println("I am now reading a book...");
            }
        };
    }

    interface ThingsToDo {
        void doIt();
    }
}