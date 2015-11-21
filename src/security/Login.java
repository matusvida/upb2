package security;//////////////////////////////////////////////////////////////////////////
// TODO:                                                                //
// Uloha2: Upravte funkciu na prihlasovanie tak, aby porovnavala        //
//         heslo ulozene v databaze s heslom od uzivatela po            //
//         potrebnych upravach.                                         //
// Uloha3: Vlozte do prihlasovania nejaku formu oneskorenia.            //
//////////////////////////////////////////////////////////////////////////

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import security.Database;
import security.Database.MyResult;

public class Login {
    protected static MyResult prihlasovanie(String meno, String heslo) throws IOException, Exception{
        /*
        *   Delay je vhodne vytvorit este pred kontolou prihlasovacieho mena.
        */
        Timer timer = new Timer();

        MyResult account = Database.find("hesla.txt", meno);
        if (!account.getFirst()){
            try{

            }
            catch(Exception e){
                System.out.println("chybny timer");
            }
            return new MyResult(false, "Nespravne meno.");
        }
        else {
            StringTokenizer st = new StringTokenizer(account.getSecond(), ":");
            st.nextToken();      //prvy token je prihlasovacie meno
            /*
            *   Pred porovanim hesiel je nutne k heslu zadanemu od uzivatela pridat prislusny salt z databazy a nasledne tento retazec zahashovat.
            */
            /* HASH*/
            String hashedDbPassword = "";
            for (int index = 0; index < st.countTokens()-1; index++){
                hashedDbPassword = st.nextToken();
            }
            System.out.println("nacitanie hash password:"+hashedDbPassword + "<<");
            String salt = st.nextToken();
            System.out.println(salt);

            MessageDigest msg = null;
            try {
                msg = MessageDigest.getInstance("SHA");
                msg.update((heslo + salt).getBytes());
            }
            catch(NoSuchAlgorithmException e){
                e.printStackTrace();
            }
            String encryptedPassword = (new BigInteger(msg.digest()).toString(16));
            System.out.println(("encBLG:"+new BigInteger(msg.digest()).toString(16)));

            System.out.println("encrypt"+encryptedPassword);

            ////dorobit !!!!!!!!!!!!!!!!!!!!!!!!!!!!
            boolean rightPassword = encryptedPassword.equals(hashedDbPassword);
            System.out.println("hashed:"+hashedDbPassword);
            if (!rightPassword)
                return new MyResult(false, "Nespravne heslo.");
        }
        return new MyResult(true, "Uspesne prihlasenie.");
    }

}
