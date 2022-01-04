public class Login{
    @Test
    public void testEmailIsNotEmpty() {
        String mockCorrectUsername = "User123";
        assertEquals(mockCorrectEmail.length(), 24);
    }

    @Test
    public void testPasswordIsNotEmpty() {
        String mockpassword = "user123";
        assertEquals(true, !mockpassword.isEmpty());
    }

    @Test
    public void testPasswordCorrectFormat() {
        String mockPassword = "User123";
        Pattern passwordREGEX = Pattern.compile(
                "^" +
                        "(?=.*[0-9])" +         //at least 1 digit
                        "(?=.*[a-z])" +         //at least 1 lower case letter
                        "(?=.*[A-Z])" +         //at least 1 upper case letter
                        "(?=.*[a-zA-Z])" +      //any letter
                        "(?=\\S+$)" +           //no white spaces
                        ".{8,}" +               //at least 8 characters
                        "$"
        );
        boolean actualPassword = passwordREGEX.matcher(mockPassword).matches();
        assertEquals(true, actualPassword);
    }

    @Test
    public void testPasswordIncorrectFormat() {
        String mockPassword = "user123";
        Pattern passwordREGEX = Pattern.compile(
                "^" +
                        "(?=.*[0-9])" +         //at least 1 digit
                        "(?=.*[a-z])" +         //at least 1 lower case letter
                        "(?=.*[A-Z])" +         //at least 1 upper case letter
                        "(?=.*[a-zA-Z])" +      //any letter
                        "(?=\\S+$)" +           //no white spaces
                        ".{8,}" +               //at least 8 characters
                        "$"
        );
        boolean actualPassword = passwordREGEX.matcher(mockPassword).matches();
        assertEquals(false, actualPassword);
    }
}