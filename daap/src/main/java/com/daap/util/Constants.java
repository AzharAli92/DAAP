package com.daap.util;

/**
 * Created by ALI on 10/1/2017.
 */
public class Constants {

    public static final String ANDROID_MANIFEST = "AndroidManifest.xml";

    public static final String CONTEXT = "Context";


    public static final String COLOR_VAL = "@color";
    public static final String COLOR_HEX = "#";

    public static final String View = "View";
    public static final String TextView = "TextView";
    public static final String AutoCompleteTextView = "AutoCompleteTextView";
    public static final String EditText = "EditText";
    public static final String Button = "Button";
    public static final String ImageView = "ImageView";
    public static final String ImageButton = "ImageButton";
    public static final String CheckBox = "CheckBox";
    public static final String RadioGroup = "RadioGroup";
    public static final String RadioButton = "RadioButton";
    public static final String ListView = "ListView";
    public static final String Spinner = "Spinner";
    public static final String LinearLayout = "LinearLayout";
    public static final String RelativeLayout = "RelativeLayout";
    public static final String FrameLayout = "FrameLayout";
    public static final String TableLayout = "TableLayout";
    public static final String GridLayout = "GridLayout";
    public static final String GridView = "GridView";
    public static final String AbsoluteLayout = "AbsoluteLayout";
    public static final String ViewPager = "ViewPager";
    public static final String RecyclerView = "RecyclerView";

    public static final String List = "List";
    public static final String ArrayList = "ArrayList";
    public static final String LinkedList = "LinkedList";
    public static final String LinkedHashSet = "LinkedHashSet";
    public static final String LinkedHashMap = "LinkedHashMap";
    public static final String Vector = "Vector";
    public static final String HashMap = "HashMap";
    public static final String Map = "Map";
    public static final String TreeMap = "TreeMap";
    public static final String Hashtable = "Hashtable";
    public static final String Iterator = "Iterator";
    public static final String HashSet = "HashSet";
    public static final String Collection = "Collection";

    public static final String Bitmap = "Bitmap";

    public static final String CHOOSE_ANTI_PATTERN = "Choose a Anti Pattern";
    public static final String A_LEAKING_INNER_CLASS = "Leaking Inner Class";
    public static final String A_MEMBER_IGNORING_METHOD = "Method Ignoring Method";
    public static final String A_LEAKING_THREAD = "Leaking Thread";
    public static final String A_INTERNAL_GETTER_SETTER = "Internal Getter Setter";
    public static final String A_INEFFICIENT_DATA_STRUCTURE = "Inefficient Data Structure";
    public static final String A_INEFFICIENT_DATA_FORMAT_PARSER = "Inefficient Data Format and Parser";
    public static final String A_SLOW_FOR_LOOP = "Slow For Loop";
    public static final String A_PUBLIC_DATA = "Public Data";
    public static final String A_LOW_MEMORY = "Low Memory";
    public static final String A_UTILITY_CLASS = "Utility Class";
    public static final String A_DURABLE_WAKE_LOCK = "Durable Wake Lock";
    public static final String A_RIGID_ALARM_MANAGER = "Rigid Alarm Manager";
    public static final String A_UNCLOSED_CLOSEABLE = "Unclosed Closeable";
    public static final String A_DROPPED_DATA = "Dropped Data";

    public static final String A_STATIC_CONTEXT = "Static Context";
    public static final String A_STATIC_VIEW = "Static View";
    public static final String A_STATIC_BITMAP = "Static Bitmap";
    public static final String A_ALIVE_LISTENERS = "Alive Listeners";
    public static final String A_VIEWS_IN_COLLECTION = "Collection of Views";
    public static final String A_COLLECTION_BITMAPS = "Collection of Bitmaps";
//    public static final String A_NO_IMAGE_LIBRARY = "No Image Library";

    public static final String A_DEBUGGABLE_RELEASE = "Debuggable Release";
    public static final String A_CONFIG_CHANGES = "Config Changes";
    public static final String A_NOT_DESCRIPTIVE_UI = "Not Descriptive UI";
    public static final String A_NESTED_LAYOUT = "Nested Layout";
    public static final String A_UNTOUCHABLE = "Untouchable";
    public static final String A_UNCONTROLLED_FOCUS_ORDER = "Uncontrolled Focus Order";
    public static final String A_OVERDRAWN_PIXEL = "Overdrawn Pixel";


    public enum SELECTED_AP_ANDROID {
        NONE, LEAKING_INNER_CLASS, MEMBER_IGNORING_METHOD, LEAKING_THREAD, INTERNAL_GETTER_SETTER,
        INEFFICIENT_DATA_STRUCTURE, INEFFICIENT_DATA_FORMAT_PARSER, SLOW_FOR_LOOP, PUBLIC_DATA, LOW_MEMORY,
        DURABLE_WAKE_LOCK, RIGID_ALARM_MANAGER, UNCLOSED_CLOSEABLE, STATIC_CONTEXT, STATIC_VIEW,
        STATIC_BITMAP, VIEWS_IN_COLLECTION, COLLECTION_BITMAPS, DROPPED_DATA, DEBUGGABLE_RELEASE,
        CONFIG_CHANGES, NOT_DESCRIPTIVE_UI, NESTED_LAYOUT, UNTOUCHABLE, UNCONTROLLED_FOCUS_ORDER,
        OVERDRAWN_PIXEL;

        @Override
        public String toString() {
            switch (this) {
                case LEAKING_INNER_CLASS:
                    return Constants.A_LEAKING_INNER_CLASS;
                case MEMBER_IGNORING_METHOD:
                    return Constants.A_MEMBER_IGNORING_METHOD;
                case LEAKING_THREAD:
                    return Constants.A_LEAKING_THREAD;
                case INTERNAL_GETTER_SETTER:
                    return Constants.A_INTERNAL_GETTER_SETTER;
                case INEFFICIENT_DATA_STRUCTURE:
                    return Constants.A_INEFFICIENT_DATA_STRUCTURE;
                case INEFFICIENT_DATA_FORMAT_PARSER:
                    return Constants.A_INEFFICIENT_DATA_FORMAT_PARSER;
                case SLOW_FOR_LOOP:
                    return Constants.A_SLOW_FOR_LOOP;
                case PUBLIC_DATA:
                    return Constants.A_PUBLIC_DATA;
                case LOW_MEMORY:
                    return Constants.A_LOW_MEMORY;
                case DURABLE_WAKE_LOCK:
                    return Constants.A_DURABLE_WAKE_LOCK;
                case RIGID_ALARM_MANAGER:
                    return Constants.A_RIGID_ALARM_MANAGER;
                case UNCLOSED_CLOSEABLE:
                    return Constants.A_UNCLOSED_CLOSEABLE;
                case STATIC_CONTEXT:
                    return Constants.A_STATIC_CONTEXT;
                case STATIC_VIEW:
                    return Constants.A_STATIC_VIEW;
                case STATIC_BITMAP:
                    return Constants.A_STATIC_BITMAP;
//                case ALIVE_LISTENERS:
//                    return Constants.A_ALIVE_LISTENERS;
                case VIEWS_IN_COLLECTION:
                    return Constants.A_VIEWS_IN_COLLECTION;
                case COLLECTION_BITMAPS:
                    return Constants.A_COLLECTION_BITMAPS;
                case DROPPED_DATA:
                    return Constants.A_DROPPED_DATA;
                case DEBUGGABLE_RELEASE:
                    return Constants.A_DEBUGGABLE_RELEASE;
                case CONFIG_CHANGES:
                    return Constants.A_CONFIG_CHANGES;
                case NESTED_LAYOUT:
                    return Constants.A_NESTED_LAYOUT;
                case NOT_DESCRIPTIVE_UI:
                    return Constants.A_NOT_DESCRIPTIVE_UI;
                case UNTOUCHABLE:
                    return Constants.A_UNTOUCHABLE;
                case UNCONTROLLED_FOCUS_ORDER:
                    return Constants.A_UNCONTROLLED_FOCUS_ORDER;
                case OVERDRAWN_PIXEL:
                    return Constants.A_OVERDRAWN_PIXEL;
                case NONE:
                    break;
            }
            return super.toString();
        }
    }


    public enum XmlAntiPattern {
        DEBUGGABLE_RELEASE, CONFIG_CHANGES, NOT_DESCRIPTIVE_UI, UNTOUCHABLE, NESTED_LAYOUT, UNCONTROLLED_FOCUS_ORDER, OVERDRAWN_PIXEL;

        @Override
        public String toString() {
            switch (this) {
                case DEBUGGABLE_RELEASE:
                    return A_DEBUGGABLE_RELEASE;
                case CONFIG_CHANGES:
                    return A_CONFIG_CHANGES;
                case NOT_DESCRIPTIVE_UI:
                    return A_NOT_DESCRIPTIVE_UI;
                case UNTOUCHABLE:
                    return A_UNTOUCHABLE;
                case NESTED_LAYOUT:
                    return A_NESTED_LAYOUT;
                case UNCONTROLLED_FOCUS_ORDER:
                    return A_UNCONTROLLED_FOCUS_ORDER;
                case OVERDRAWN_PIXEL:
                    return A_OVERDRAWN_PIXEL;
            }
            return super.toString();
        }
    }
}
