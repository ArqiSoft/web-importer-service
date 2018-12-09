/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sds.webimporter.wikitools.access;

public class Namespace {

    private Namespace() { }
    
    public static final int MEDIA = -2;
    public static final int SPECIAL = -1;
    public static final int ARTICLE = 0;
    public static final int TALK = 1;
    public static final int USER = 2;
    public static final int USERTALK = 3;
    public static final int PROJECT = 4;
    public static final int PROJECTTALK = 5;
    public static final int IMAGE = 6;
    public static final int IMAGETALK = 7;
    public static final int MEDIATALK = 8;
    public static final int MEDIAWIKITALK = 9;
    public static final int TEMPLATE = 10;
    public static final int TEMPLATETALK = 11;
    public static final int HELP = 12;
    public static final int HELPTALK = 13;
    public static final int CATEGORY = 14;
    public static final int CATEGORYTALK = 15;
    public static final int UNKNOWN = -3;
}
