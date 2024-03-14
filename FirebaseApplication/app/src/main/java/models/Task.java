package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Task implements Serializable {
    private String title;
    private boolean isMandatory;
    private int questionsCount;

    private String key;
    private String authorKey;

    @Deprecated
    public static final ArrayList<Task> GLOBAL_DATA = new ArrayList();

    static {
        Random rand = new Random();
        GLOBAL_DATA.add(new Task("Задание на классы", true, 10));
        for (int i = 0; i < 20; i++) {
            Task t = new Task("Task-" + i,
                    rand.nextInt(2) == 0,
                    rand.nextInt(7) + 3);
            GLOBAL_DATA.add(t);
        }
    }

    @Override
    public String toString() {
        return title + ", " + isMandatory + ", " + questionsCount;
    }

    public Task(){}
    public Task(String title, boolean isMandatory, int questionsCount) {
        this.title = title;
        this.isMandatory = isMandatory;
        this.questionsCount = questionsCount;
    }

    public String getAuthorKey() {
        return authorKey;
    }

    public void setAuthorKey(String authorKey) {
        this.authorKey = authorKey;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    public int getQuestionsCount() {
        return questionsCount;
    }

    public void setQuestionsCount(int questionsCount) {
        this.questionsCount = questionsCount;
    }
}
