package kovalenko.vika.common.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PathsJsp {
    INDEX_JSP("/WEB-INF/index.jsp"),
    START_JSP("/WEB-INF/start.jsp"),
    QUEST_JSP("/WEB-INF/quest.jsp"),
    END_JSP("/WEB-INF/end.jsp");

    private final String path;

    @Override
    public String toString() {
        return path;
    }

}
