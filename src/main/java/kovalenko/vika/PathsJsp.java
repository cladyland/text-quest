package kovalenko.vika;

public enum PathsJsp {
    INDEX_JSP("/WEB-INF/index.jsp"),
    START_JSP("/WEB-INF/start.jsp"),
    QUEST_JSP("/WEB-INF/quest.jsp"),
    END_JSP("/WEB-INF/end.jsp");

    private final String path;
    PathsJsp(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }

}
