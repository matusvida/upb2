package security;//////////////////////////////////////////////////////////////////////////
// TODO:                                                                //
// Uloha1: Do suboru s heslami ulozit aj sal.                           //
// Uloha2: Pouzit vytvorenu funkciu na hashovanie a ulozit heslo        //
//         v zahashovanom tvare.                                        //
//////////////////////////////////////////////////////////////////////////

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import security.Database;
import security.Database.MyResult;


public class Registration {
    protected static MyResult registracia(String meno, String heslo) throws NoSuchAlgorithmException, Exception{
        if (Database.exist("hesla.txt", meno)){
            System.out.println("Meno je uz zabrate.");
            return new MyResult(false, "Meno je uz zabrate.");
        }
        else {
            /*
            *   Salt sa obvykle uklada ako tretia polozka v tvare [meno]:[heslo]:[salt].
            */

            if(passwordValidation(heslo)==false){
              return new MyResult(false, "");
            }
            byte[] salt = new byte[32];
            Database.add("hesla.txt", meno, heslo, salt.toString());
        }
        return new MyResult(true, "");
    }

    protected static boolean passwordValidation(String password){
        boolean lowerCase = false;
        boolean upperCase = false;
        boolean digit = false;
        boolean result = false;
        for (int i=0; i<password.length(); i++){
            if(Character.isUpperCase(password.charAt(i))){
                upperCase = true;
            }
            if(Character.isDigit(password.charAt(i))){
                digit = true;
            }
            if(Character.isLowerCase(password.charAt(i))){
                lowerCase = true;
            }
        }
        if(lowerCase && upperCase && digit){
            result = true;
        }
        return result;
    }
    
}
