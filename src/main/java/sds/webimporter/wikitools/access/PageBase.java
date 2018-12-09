/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sds.webimporter.wikitools.access;

import org.joda.time.DateTime;


public abstract class PageBase {

    public String Name;
    public String Title;
    public String Touched;
    public Boolean Redirect;
    public Boolean Exists;
    public int Length;
    public Boolean Loaded;
    public String Text;
}
