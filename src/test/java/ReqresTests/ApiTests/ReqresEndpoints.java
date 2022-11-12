package ReqresTests.ApiTests;

public final class ReqresEndpoints {

    public static final String basePath = "https://reqres.in/api";

    public static final String singleUser = "/users/{id}";
    public static final String createSingleUser = "/users";
    public static final String usersList = "/users?page={pageNumber}";
    public static final String login = "/login";
    public static final String register = "/register";
    public static final String singleUnknown = "/unknown/{id}";
    public static final String unknownList = "/unknown";
}
