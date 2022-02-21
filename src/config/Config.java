//Copyright (c) 2011, 2022, ЗАО "НПЦ "АТТРАНС"
package config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import static java.lang.System.getProperty;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.sql.DataSource;
import oracle.jdbc.pool.OracleDataSource;
//import org.postgresql.ds.PGSimpleDataSource;//PGPoolingDataSource;
import org.postgresql.ds.PGPoolingDataSource;
import org.postgresql.jdbc2.optional.PoolingDataSource;
//import org.postgresql.ds.PGConnectionPoolDataSource;
//import org.postgresql.xa.PGXADataSource;
//-Djava.library.path=/opt/attrans/lib
class Config {

    private static String ATTRANS_HOME;

    private static String shema = "amur";//25
    private static String db = "172.16.0.37";
  
//    private static String usr = "cmk";//11
//    private static String pwd = "cmk";
   
//    private static String usr = "azot";//13
//    private static String pwd = "azot";    
//    private static String usr = "novosoda";//14
//    private static String pwd = "novosoda"; 
//    private static String usr = "dena";//15*
//    private static String pwd = "dena";      
//    private static String usr = "denb";//16*
//    private static String pwd = "denb";    
//    private static String usr = "zavodskaja";//17
//    private static String pwd = "zavodskaja";      
//    private static String usr = "podolsk";//18*
//    private static String pwd = "podolsk";
//    private static String usr = "luga1";//19
//    private static String pwd = "luga1";
//    private static String usr = "verh";//20
//    private static String pwd = "verh";
//    private static String usr = "zapsib2";//21
//    private static String pwd = "zapsib2";    
//    private static String usr = "soda1";//22
//    private static String pwd = "soda";
//    private static String usr = "crossing01";//23
//    private static String pwd = "crossing";       
//    private static String usr = "bpzt";//24
//    private static String pwd = "bpzt";  
//    private static String usr = "blag";//25
//    private static String pwd = "blag";      
//    private static String usr = "tomsk";//26
//    private static String pwd = "tomsk";  
//    private static String usr = "soda_new";//28
//    private static String pwd = "soda";      
//    private static String usr = "viksa";//29
//    private static String pwd = "viksa";
//    private static String usr = "proshiv";//30
//    private static String pwd = "proshiv";    
//    private static String usr = "viksa_przd1";//31
//    private static String pwd = "viksa_przd1";   
//    
//    private static String usr = "ugol";//33
//    private static String pwd = "ugol";    
    
//    private static String usr = "sibmine";//34
//    private static String pwd = "sibmine";    

//    private static String usr = "sibmine1";//34
//    private static String pwd = "sibmine";      
//    private static String usr = "elga7";//35
//    private static String pwd = "elga7";    
    
//    private static String usr = "elga8";//36
//    private static String pwd = "elga8"; 

//    private static String usr = "elga9";//37
//    private static String pwd = "elga9";     

//    private static String usr = "elgab";//38
//    private static String pwd = "elgab";  

//    private static String usr = "elga10";//39
//    private static String pwd = "elga10";  

//    private static String usr = "elga11";//40
//    private static String pwd = "elga11";  

//    private static String usr = "elga12";//41
//    private static String pwd = "elga12";  
    
//    private static String usr = "acdu";//42
//    private static String pwd = "asdu";

//    private static String usr = "ulak";//43
//    private static String pwd = "ulak";      

    private static String usr = "amur";//44
    private static String pwd = "amur"; 

    private static int db_type = 0;//0-oracle 1-postgre
//    private static boolean n = false; //выгружатиь новые вьюхи или старые n = true;

    private static String file;
    private static java.io.File fpw;
    private static java.io.PrintWriter pw = null;

    private static javax.sql.DataSource ds;
    private static java.sql.Connection con;
    private static java.sql.Statement st;
    private static int id = 0;

    private static final java.text.SimpleDateFormat SDF = new java.text.SimpleDateFormat("dd-MM-yyyy_HH.mm.ss");
    private static final String FS = getProperty("file.separator");
//    private void sign (){
//        this.getClass().
//    }
//    private static String area;

    public static void main(String[] args) {
        ATTRANS_HOME = System.getenv("ATTRANS_HOME");// получить env
        if ("".equals(ATTRANS_HOME) || ATTRANS_HOME == null || "null".equals(ATTRANS_HOME)) {
            System.out.println("NOT SET VARIABLE - ATTRANS_HOME !!!");
//            System.exit(1);
        ATTRANS_HOME="/opt/attrans";
        }

        if (args.length != 0) {
            Args(args);
        }
        if (id !=0){
            pr();    
        }
        go();
    }

    //<editor-fold defaultstate="collapsed" desc="args">
    private static void Args(String[] args) {
        for (int i = 0; i < args.length; i++) { //
            if (args[i].toLowerCase().equals("-u") | args[i].toLowerCase().equals("--user")) {
                if ((args.length - 1) > i) {
                    usr = args[i + 1];
                    shema = usr;
                    System.out.println("db user : " + args[i + 1]);
                } else {
                    System.out.println("не указан второй параметр - имя пользователя.");
                }
            }

            if (args[i].toLowerCase().equals("-p") | args[i].toLowerCase().equals("--password")) {
                if ((args.length - 1) > i) {
                    pwd = args[i + 1];
                    System.out.println("db password : " + args[i + 1]);
                } else {
                    System.out.println("не указан второй параметр - пароль.");
                }
            }

            if (args[i].toLowerCase().equals("--db")) {
                if ((args.length - 1) > i) {
                    db = args[i + 1];
                    System.out.println("db name : " + args[i + 1]);
                } else {
                    System.out.println("не указан второй параметр - имя или IP базы данных.");
                }
            }

            if (args[i].toLowerCase().equals("-t") | args[i].toLowerCase().equals("--type")) {
                if ((args.length - 1) > i) {
                    int t = test_String2(args[i + 1]);
                    if (t < 0 || t > 1) {
                        System.out.println("type - wrong number");
                    } else {
                        db_type = t;
                        System.out.println("type: " + args[i + 1]);
                    }
                } else {
                    System.out.println("не указан второй параметр - тип базы данных.");
                }
            }

            if (args[i].toLowerCase().equals("--help")) {
                Usage();
            }

            if (args[i].toLowerCase().equals("-n") | args[i].toLowerCase().equals("--id")) {
                if ((args.length - 1) > i) {
                    int n = test_String2(args[i + 1]);
                    if (n < 0 || n > 100) {
                        System.out.println("number of config - wrong number");
                    } else {
                        id = n;
                        System.out.println("id: " + args[i + 1]);
                    }
                } else {
                    System.out.println("не указан второй параметр - id базы данных.");
                }
            }
        }
    }
//</editor-fold>

    private static void Usage() {
        System.out.println("Export data configuration:");
        System.out.println("Usage: java -jar Create.jar [options]");
        System.out.println("    -u <user> | --user <user>");
        System.out.println("    -p <password> | --password <password>");
        System.out.println("    -f <output file> | --file <output file>");
        System.out.println("    --db <name or IP database");
        System.out.println("    --type <Oracle or PostgreSQL>");
        System.out.println("    --port <port> -f --file");
        System.out.println("    --help (this)");
        System.out.println("    -n <num config>| --id <num config>");
    }

    private static int test_String2(String arg) {
        int i;
        try {
            i = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            i = 0;
        }
        return i;
    }

    private static void go() {
        long tm = System.nanoTime();
        
        Create_DS();
        Create_con();
        CreateFile(file + ".att");
String txt = "#Copyright (c) 2011, 2022, ЗАО \"НПЦ \"АТТРАНС\"";
save(txt);
        GetLineFromInitWV("VI_WINDOWS");
        GetLineFromInitWV("vi_colors");
        GetLineFromInitWV("VI_DEFAULTS");
        GetLineFromInitWV("VI_OBJ_TYPES");// FFF Translate
        GetLineFromInitWV("VI_NOTE_TYPES");
        GetLineFromInitWV("VI_MESS");
        GetLineFromInitWV("VI_CMDS");
        GetLineFromInitWV("VI_CMDMESS");
//        GetLineFromInitWV("VI_COM_SERVERS");  // ? для коммуникационника 
        GetLineFromInitWV("VI_TXT");        

//        SelectAllInitViews();
        GetLineFromInitWV("VI_BORDERS");
        GetLineFromInitWV("VI_AREAS");
        GetLineFromInitWV("VI_COUNT_UNITS"); //сначлала идут COUNT_UNIT в них добавляются BEAM (лучи)
        GetLineFromInitWV("VI_BEAMS");//plug count
        GetLineFromInitWV("VI_COUNTERS");//датчики добавляются в лучи
        GetLineFromInitWV("VI_CABINETS");//это идет в юниты //FFF
        GetLineFromInitWV("VI_CHANNELS");//это идет в юниты //FFF //FFF !!!!!!!!!!!!!!!!!!!!!!!!!!!!! вьюху CHANNELS не нужна
        GetLineFromInitWV("VI_PLCS");//CPU OBJ for Controller status
        GetLineFromInitWV("VI_CROSSINGS");
        GetLineFromInitWV("VI_DEADLOCKS");
        GetLineFromInitWV("VI_RAILNET_COUNTERS");//for railnet boom
        GetLineFromInitWV("VI_RAILNETS");//перед turnout т.к. турнауты берут раилнеты
        GetLineFromInitWV("VI_TURNOUTS");//перед direct т.к. директы берут турнауты
        GetLineFromInitWV("VI_DIRECTS");
        GetLineFromInitWV("VI_FANS"); //if in defaults fan = true
        GetLineFromInitWV("VI_LIGHTS");
//---
        GetLineFromInitWV("VI_LIGHTS_NEW");
        GetLineFromInitWV("VI_LIGHTS_PN");
//---
        GetLineFromInitWV("VI_MPABS"); //if in defaults mpab = true
        GetLineFromInitWV("VI_NEARBYS");
        GetLineFromInitWV("VI_PARAMS");//---------------это идет в юниты //FFF !!!!!!!!!!!!!!!!!!!!!!!!!!!!! вьюху PARAMS не нужна
        GetLineFromInitWV("VI_PROTECTEDS");
        GetLineFromInitWV("VI_UNITS");
        GetLineFromInitWV("VI_F3_DIO_20_8");
        GetLineFromInitWV("VI_UNRULEDS");
        GetLineFromInitWV("VI_VCS");

//-----------------------------new----------------------------------------------
        GetLineFromInitWV("VI_WEIGHERS");
        GetLineFromInitWV("VI_ELEVATEDTRACKS");
        GetLineFromInitWV("VI_UKSPS");
        GetLineFromInitWV("VI_DGAS");
        GetLineFromInitWV("VI_HEATS");
        GetLineFromInitWV("VI_HEATINGS");
        GetLineFromInitWV("VI_GATE");
        GetLineFromInitWV("VI_PUSHERS");        
        GetLineFromInitWV("VI_US");        
        

        GetLineFromInitWV("VI_VZREZ");
        GetLineFromInitWV("V_OBJ_OFFSETS");
        GetLineFromInitWV("V_BEAM_KUSO");
        GetLineFromInitWV("VI_OBJ_TYPES");//for developer
        tm = System.nanoTime() - tm;
        System.out.println("Итого : " + tm / 1000000000.0 + " сек.");
        
        CreateZip(file);
        CloseFile();
        CreateFile(file+".usr");
        GetLineFromInitWV("VI_USERS");
        CloseFile();
        
        CreateFile("test"+".cfg");
//        GetLineFromInitWV("VI_PLACE_TYPE");
//        GetLineFromInitWV("VI_PLACE");
        CloseFile();        
        
        CreateFile("result"+".cfg");
//        GetLineFromInitWV("VI_SKID");
        CloseFile();        
    }

//------------------------------------------------------------------------------
    private static int GetLineFromInitWV(String View) {
        int j = 0;

//            if (tableExists(View)){
//                try (ResultSet rs = con.createStatement().executeQuery("SELECT * FROM " + View);) {
                try (ResultSet rs = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM " + View);) {
                    ResultSetMetaData rsmd = rs.getMetaData();// get result set meta data
                    int numberOfColumns = rsmd.getColumnCount();
                    
                    String comment = "#";
                    if("VI_USERS".equals(View)){
                        save("#Copyright (c) 2011, 2021, ЗАО \"НПЦ \"АТТРАНС\"");
                    }
                    save("[" + View.toUpperCase() + "]");
                    
                    for (int i = 1; i <= numberOfColumns; i++) {
                        String Tag = rsmd.getColumnName(i).toUpperCase();
                        if (i == numberOfColumns) {
                            comment = comment + (i - 1) + "=" + Tag;
                        } else {
                            comment = comment + (i - 1) + "=" + Tag + ",";
                        }
                    }
                    save(comment);//ARGUMENS
                    
                    if("VI_USERS".equals(View)){
                        save("#timestamp "+new java.util.Date().getTime());
                    }
                    
                    while (rs.next()) {
                        String line = "";
                        for (int i = 1; i < numberOfColumns + 1; i++) {// get the column names; column indexes start from 1
                            
                            line += rs.getString(i);

                            if (i != numberOfColumns) {
                                line += ",";
                            }
                        }
                        save(line);
                        j++;
                    }//end while
                } catch (java.sql.SQLException e) {
                    sql_err(e);
                }
//            }
        
        return j;
    }
    
    private static boolean tableExists(String View) {
        boolean t;
        try {
//            DatabaseMetaData dbm = con.getMetaData();
            ResultSet rs = con.getMetaData().getTables(null, usr.toUpperCase(), View, null);// check if View table is there
            if(rs.next()){
                t = true;
                System.out.println("Table "+rs.getString("TABLE_NAME")+" exists !");
            } else {
                t = false;
                System.out.println("Table "+rs.getString("TABLE_NAME")+" no exists !!");
            }
        } catch (java.sql.SQLException ex) {
            t = false;
        }
        return t;
    }

//    private void SelectAllInitViews() {
//        try (ResultSet rs = con.createStatement().executeQuery("select view_name from user_views where view_name like 'VW_INIT_%'");) {
//            while (rs.next()) {
//                String name = rs.getString("view_name");//имя
//                GetLineFromInitWV(name);
//            }//end while
//        } catch (SQLException e) {
//            sql_err(e);
//        }
//    }
    private static void CreateFile(String fileName) {
        
        java.io.File dir = new java.io.File(ATTRANS_HOME + FS + "config");

        fpw = new java.io.File(ATTRANS_HOME + FS + "config" + FS + fileName);
        System.out.println("Файл : " +ATTRANS_HOME + "" + FS + "config" + FS +""+ fileName);
        if (!dir.exists()) {
            dir.mkdir();
        }
        try {
            pw = new PrintWriter(fpw, "UTF-8");
        } catch (java.io.FileNotFoundException | UnsupportedEncodingException e) {
            err(e);
        }
    }

    private static void save(String line) {
        if (fpw.canWrite()) {
            pw.println(line);
            pw.flush();
        }
    }
    
    private static void CloseFile(){
        pw.close();
    }

    private static void err(java.lang.Exception e) {
        java.util.logging.Logger.getLogger(Error.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
    }

    private static void sql_err(java.sql.SQLException e) {
        java.util.logging.Logger.getLogger(Error.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
    }
//==============================================================================

    private static javax.sql.DataSource Create_DS() {
        try {
            // Set connection properties
            if (db_type == 1) {
//                PGXADataSource pds = new PGXADataSource();
                PGPoolingDataSource pds = new PoolingDataSource();
//                PGSimpleDataSource pds = new PGSimpleDataSource();
//                PGConnectionPoolDataSource pds = new PGConnectionPoolDataSource();
//                pds.setDataSourceName("PostgreSQL Data Source");
                pds.setServerName(db);
                pds.setDatabaseName(shema);
                pds.setUser(pwd);
                pds.setPassword(pwd);
//                pds.setMaxConnections(3);
//                ds.setPortNumber(Integer.parseInt(PORT));
                ds = (DataSource) pds;
            } else {
                java.util.Locale.setDefault(java.util.Locale.US);
                OracleDataSource ods = new OracleDataSource();
                String url = "jdbc:oracle:thin:@//" + db + ":1521/xe";                
//                String url = "jdbc:oracle:thin:@//" + db + ":1521/xepdb1";
//                String url = "jdbc:oracle:thin:@//" + DB + ":" + PORT + "/xe";
                ods.setUser(usr);
                ods.setPassword(pwd);
                ods.setURL(url);
//                ods.setConnectionCachingEnabled(true);//эта штука нужна для создания логических коннекшенов может вызвать ошибку если базой не поддерживается.
                ds = ods;
            }
        } catch (java.sql.SQLException e) {
            System.out.println("");
            System.out.println(e.getMessage());
//            ret = ex.getErrorCode();
            sql_err(e);
        }//end catch

        return ds;
    }

    private static java.sql.Connection Create_con() {
        try {
            con = ds.getConnection();
//========================================================
            try (ResultSet rs = con.createStatement().executeQuery("select id_obj from area");) {
                while (rs.next()) {
                    file = rs.getString("ID_OBJ");//
//                    file += ".att";
                }//end while
                System.out.println("Save to file: " + file);
            } catch (java.sql.SQLException e) {
                sql_err(e);
            }
//========================================================
        } catch (java.sql.SQLException e) {//обработать исключение
            if (db_type == 0) {//если оракл
                switch (e.getErrorCode()) {
                    case 1_017:
                        log("error in user name or password.");
                        break;
                    case 17_002:
                        log("Нет соединения с базой данных " + db);
                        break;
                    case 28_000:
                        log("Учетная запись заблокирована");
                        break;
                    default:
                        log(e.getMessage());
                        break;
                }//end switch
            }//end if
            sql_err(e);
        }//end catch
        return con;
    }

    private static java.sql.Statement Create_st() {
        try {
            st = con.createStatement();
        } catch (SQLException e) {//обработать исключение
            if (db_type == 0) {//если оракл
                switch (e.getErrorCode()) {
                    case 1_017:
                        log("error in user name or password.");
                        break;
                    case 17_002:
                        log("Нет соединения с базой данных " + db);
                        break;
                    case 28_000:
                        log("Учетная запись заблокирована");
                        break;
                    default:
                        log(e.getMessage());
                        break;
                }//end switch
            }//end if
            sql_err(e);
        }//end catch
        return st;
    }

    private static void log(String message) {
        System.out.println(DateTime() + " - " + message);
    }

//<editor-fold defaultstate="collapsed" desc="DateTime()">
    private static String DateTime() {
        return SDF.format(new java.util.Date());
    }//</editor-fold>
    
    private static void pr() {
        switch (id) {
            case 11://11 - cmk
                usr = "cmk";
                pwd = "cmk";
                break;
            case 13://13 - azot
                usr = "azot";
                pwd = "azot";
                break;
            case 14://14 - novosoda
                usr = "novosoda";
                pwd = "novosoda";
                break;                
            case 15://15 - den a
                break;
            case 16://16 - den b
                break;
            case 17://17 - zavodskaja
                usr = "zavodskaja";
                pwd = "zavodskaja";
                break;                
            case 18://18 - podolsk
                usr = "podolsk";
                pwd = "podolsk";
                break;
            case 19://19 - luga
                break;                
            case 20://20 - verh
                break;                
            case 21://21 - zapsib2
                usr = "zapsib2";
                pwd = "zapsib2";
                break;                
            case 22://22 - soda2
                break;     
            case 23://23 - uznaya    //Переезд ТСБ Южная
                break;     
            case 24://24 - bpzt    //БПЖТ
                break;     
            case 25://25 - blag    //25 - Перезд Благовещенск
                break;     
            case 26://26 - tomsk    //26 - Перезд Томск
                usr = "tomsk";
                pwd = "tomsk";
                break;     
            case 27://27 - udina    //27 - Переезд Юдина (СИЮУР ТРАНС Тобольск нефтехим)
                break;     
            case 28://28 - soda_new    28 - Сода НОВАЯ                
                break; 
            case 29://29 - viksa
                usr = "viksa";
                pwd = "viksa";
                break;
            case 30://30 - proshiv
                usr = "proshiv";
                pwd = "proshiv";
                break;   
            case 31://31 - viksa_przd1
                break;
            case 32://32 - 
                break;       
            case 33://32 -
                usr = "ugol";
                pwd = "ugol";
                break;   
            case 34://32 -
                usr = "sibmine";
                pwd = "sibmine";
                break; 
            case 35://32 -
                usr = "elga7";
                pwd = "elga7";
                break; 
            case 36://32 -
                usr = "elga8";
                pwd = "elga8";
                break; 
            case 37://32 -
                usr = "elga9";
                pwd = "elga9";
                break;     
            case 38://32 -
                usr = "elgab";
                pwd = "elgab";
                break;
            case 39://32 -
                usr = "elga10";
                pwd = "elga10";
                break;
            case 40://32 -
                usr = "elga11";
                pwd = "elga11";
                break;
            case 41://32 -
                usr = "elga12";
                pwd = "elga12";
                break;
            case 42://32 -
                usr = "asdu";
                pwd = "asdu";
                break;                
            case 43://32 -
                usr = "ulak";
                pwd = "ulak";
                break;
            case 44://32 -
                usr = "amur";
                pwd = "amur";
                break;                
        }
    }
    
    private static void CreateZip(String fileName) {
//        System.out.println("file : " + fileName);
        byte[] buffer = new byte[1024];


            try ( java.util.zip.ZipOutputStream zos = new java.util.zip.ZipOutputStream(new java.io.FileOutputStream(ATTRANS_HOME + FS + "config" + FS + fileName+".zip"))) {
                
                zos.putNextEntry(new java.util.zip.ZipEntry(fileName+".att"));
                java.io.FileInputStream in = new java.io.FileInputStream(ATTRANS_HOME + FS + "config" + FS + fileName+".att");
                int len;
//                int i = 0;
                while ((len = in.read(buffer)) > 0) {
//                    if (i==0){
//                        buffer[0] = 0x0;
//                        buffer[1] = 0x0;
//                    }
                    zos.write(buffer, 0, len);
//                    i++;
                }
//                System.out.println("i = " + i);
                in.close();
                zos.closeEntry();
                System.out.println("Файл : " +ATTRANS_HOME + "" + FS + "config" + FS +""+ fileName+".zip");
            } catch (FileNotFoundException ex) {
            err(ex);
        } catch (IOException ex) {
            err(ex);
        }

    }
        
}
