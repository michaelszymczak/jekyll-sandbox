class Main {

    public static void main(String[] args) {
        thingsToDo();
    }

    static void thingsToDo() {
        System.out.println("I am now doing the laundry...");
        System.out.println("I am now walking the dog...");
        System.out.println("I am now reading a book...");
    }
}

class Main2 {

    public static void main(String[] args) {
        thingsToDo();
    }

    static void thingsToDo() {
        // a thing
        ThingsToDo thingsToDo = new ThingsToDo() {
            @Override
            public void doIt() {
                System.out.println("I am now doing the laundry...");
                System.out.println("I am now walking the dog...");
                System.out.println("I am now reading a book...");
            }
        };

        // do something else

        // do a thing
        thingsToDo.doIt();
    }

    interface ThingsToDo {
        void doIt();
    }
}

class Main3 {

    public static void main(String[] args) {
        doThings();
    }

    static void doThings() {
        // a thing
        Runnable thingsToDo = () -> {
            System.out.println("I am now doing the laundry...");
            System.out.println("I am now walking the dog...");
            System.out.println("I am now reading a book...");
        };

        doSomething(thingsToDo);
    }

    static void doSomething(Runnable thingsToDo) {
        thingsToDo.run();
    }
}