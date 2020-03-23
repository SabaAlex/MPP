package repository.postgreSQL.statements;

public enum MovieSQLStatements {
    INSERT("insert into ", " (id,title,director,yearofrelease,mainstar,genre) values(?,?,?,?,?,?)"),
    DELETE("delete from ", " where id = ");

    private final String cmdStart;
    private final String cmdEnd;

    public String composeMessage(String entity){
        return cmdStart + entity + cmdEnd;
    }

    MovieSQLStatements(String cmdStart, String cmdEnd) {
        this.cmdStart = cmdStart;
        this.cmdEnd = cmdEnd;
    }
}
