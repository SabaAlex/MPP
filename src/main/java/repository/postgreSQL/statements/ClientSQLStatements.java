package repository.postgreSQL.statements;


public enum ClientSQLStatements {
    INSERT("insert into ", " (id,firstname,lastname,age) values(?,?,?,?)"),
    DELETE("delete from ", " where id = ");

    private final String cmdStart;
    private final String cmdEnd;

    public String composeMessage(String entity){
        return cmdStart + entity + cmdEnd;
    }

    ClientSQLStatements(String cmdStart, String cmdEnd) {
        this.cmdStart = cmdStart;
        this.cmdEnd = cmdEnd;
    }
}

