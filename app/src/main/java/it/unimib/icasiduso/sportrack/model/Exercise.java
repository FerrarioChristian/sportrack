package it.unimib.icasiduso.sportrack.model;

public class Exercise {
    private String name;
    private String type;
    private String muscle;

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getMuscle() {
        return muscle;
    }

    public String getEquipment() {
        return equipment;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getInstructions() {
        return instructions;
    }

    private String equipment;
    private String difficulty;
    private String instructions;

    @Override
    public String toString() {
        return "Exercise{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", muscle='" + muscle + '\'' +
                ", equipment='" + equipment + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", instructions='" + instructions + '\'' +
                '}';
    }
}
