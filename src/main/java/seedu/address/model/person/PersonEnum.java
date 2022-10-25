package seedu.address.model.person;

public enum PersonEnum {
    Nurse("N"),
    Patient("P");

    public final String label;

    private PersonEnum(String label) {
        this.label = label;
    }
}
