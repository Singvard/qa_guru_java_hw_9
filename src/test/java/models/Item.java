package models;

public enum Item {
    BACKPACK("Sauce Labs Backpack"),
    BIKE_LIGHT("Sauce Labs Bike Light"),
    BOLT_T_SHIRT("Sauce Labs Bolt T-Shirt"),
    FLEECE_JACKET("Sauce Labs Fleece Jacket"),
    ONESIE("Sauce Labs Onesie"),
    RED_T_SHIRT("Test.allTheThings() T-Shirt (Red)");

    private final String itemName;

    Item(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String toString() {
        return this.itemName;
    }
}
