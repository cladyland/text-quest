package kovalenko.vika;

public enum PathsJsp {
    INDEX_JSP("/WEB-INF/index.jsp"),
    QUEST_JSP("/WEB-INF/quest.jsp");

    private final String path;
    PathsJsp(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }

}
