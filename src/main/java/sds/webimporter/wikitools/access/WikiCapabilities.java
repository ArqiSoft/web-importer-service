package sds.webimporter.wikitools.access;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class WikiCapabilities {
    
    /// <summary>
    /// Version of MediaWiki
    /// </summary>
    public String Version;
    /// <summary>
    /// Requierd to check users
    /// </summary>
    public Boolean HasCheckUser;
    /// <summary>
    /// Required for full template substitution
    /// </summary>
    public Boolean HasExpandTemplates;
    /// <summary>
    /// Required to get images
    /// </summary>
    public Boolean HasFilePath;
    /// <summary>
    /// Requiered to make bot via MakeBot interface and have access to MakeBot log
    /// </summary>
    public Boolean HasMakeBot;
    /// <summary>
    /// Required to determine what permission bureaucrat have
    /// </summary>
    public Boolean HasMakeSysop;
    /// <summary>
    /// Required to get new users log
    /// </summary>
    public Boolean HasNewUserLog;
    /// <summary>
    /// Required to hide revisions
    /// </summary>
    public Boolean HasOversight;
    /// <summary>
    /// Required to rename users
    /// </summary>
    public Boolean HasRenameUser;

    /// <summary>
    /// Casts capabilities to string
    /// </summary>
    /// <returns>String</returns>
    public String ToString() {
        List<String> str = new ArrayList<String>();
        str.add("!Wiki-capa");
        str.add("ext:checkuser = " + HasCheckUser);
        str.add("ext:exptl = " + HasExpandTemplates);
        str.add("ext:fpath = " + HasFilePath);
        str.add("ext:mkbot = " + HasMakeBot);
        str.add("ext:mksysop = " + HasMakeSysop);
        str.add("ext:newusers = " + HasNewUserLog);
        str.add("ext:oversight = " + HasOversight);
        str.add("ext:renameuser = " + HasRenameUser);
        return String.format("\n", str.toArray());
    }

    /// <summary>
    /// Loads capabilities from string
    /// </summary>
    /// <param name="s">String to parse</param>
    /// <returns>Succes of parsing</returns>
    public Boolean FromString(String s) {
        Scanner scanner = new Scanner(s);
        if (scanner.next() != "!Wiki-capa") {
            return false;
        }
        StringReader sr = new StringReader(s);
        while (scanner.hasNext()) {
            String str = scanner.next();
            String[] kv = str.split("=");
            kv[0] = kv[0].trim();
            kv[1] = kv[1].trim();
            switch (kv[0]) {
                case "version":
                    Version = kv[1];
                    break;
                case "ext:checkuser":
                    HasCheckUser = Boolean.valueOf(kv[1]);
                    break;
                case "ext:exptl":
                    HasExpandTemplates = Boolean.valueOf(kv[1]);
                    break;
                case "ext:fpath":
                    HasFilePath = Boolean.valueOf(kv[1]);
                    break;
                case "ext:mkbot":
                    HasMakeBot = Boolean.valueOf(kv[1]);
                    break;
                case "ext:mksysop":
                    HasMakeSysop = Boolean.valueOf(kv[1]);
                    break;
                case "ext:newusers":
                    HasNewUserLog = Boolean.valueOf(kv[1]);
                    break;
                case "ext:oversight":
                    HasOversight = Boolean.valueOf(kv[1]);
                    break;
                case "ext:renameuser":
                    HasRenameUser = Boolean.valueOf(kv[1]);
                    break;
            }
        }
        return true;
    }
}
