import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class rename {
    //private static String localDate;
    DateTimeFormatter Datef = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    static LocalDate localDate = LocalDate.now();
    static LocalTime localt = LocalTime.now();
    // get file references
    static String help = "-help";
    static String prefix = "-prefix";
    static String suffix = "-suffix";
    static String replace = "-replace";
    static String file = "-file";
    static String date = "@date";
    static String time = "@time";

    public static int find(String[] arr, String str, int len) {
        for (int i = 0; i < len; i++) {
            if (arr[i].equals(str)) {
                return i;
            }
        }
        return -1;
    }

    // Build a dictionary of key:value pairs (without the leading "-")
    static HashMap<String, Vector<String>> parse(String[] args) {
        HashMap<String, Vector<String>> arguments = new HashMap<>();
        Vector<String> replace_val = new Vector<>();
        Vector<String> files_val = new Vector<>();
        Vector<String> temp = new Vector<>();
        Vector<String> temp1 = new Vector<>();
        String key = null;
        String value = null;
        int args_num = 0;
        if (args.length == 0) {
            System.out.println("Usage: java rename [-option argument1 argument2 ..]");
            System.exit(0);
        }
        int findex = find(args, file, args.length);
        int suf_index = find(args, suffix, args.length);
        int pre_index = find(args, prefix, args.length);
        int rep_index = find(args, replace, args.length);

        // check for errors
        /// process each argument as either a key or value in the pair
        for (String entry : args) {
            // assume that keys start with a dash
            if (entry.equals(help)) {
                System.out.println("(c) 2020 Jeff Avery. Last revised: Jan 2, 2020.\n" +
                        " Usage: java rename [-option argument1 argument2 ...]\n" + "\n" +
                        "  Options:\n" +
                        "  -help                   :: display this help and exit.\n" +
                        "  -prefix [string]        :: rename the file so that it starts with [string].\n" +
                        "  -suffix [string]        :: rename the file so that it ends with [string]. \n" +
                        "  -replace [str1] [str2]  :: rename [filename] by replacing all instances of [str1] with [str2]. \n" +
                        "  -file [filename]        :: indicate the [filename] to be modified.");
                System.exit(0);
            }
            if (entry.equals(prefix)) {
                int index = pre_index;
                int num_of_arg = 0;
                String pref = new String("");
                if (args.length > index + 1) {
                    for (int j = index + 1; j < args.length; ++j) {
                        if (args[j].startsWith("-")) {
                            if (args[j].equals(help)) {
                                System.out.println("(c) 2020 Jeff Avery. Last revised: Jan 2, 2020.\n" +
                                        " Usage: java rename [-option argument1 argument2 ...]\n" + "\n" +
                                        "  Options:\n" +
                                        "  -help                   :: display this help and exit.\n" +
                                        "  -prefix [string]        :: rename the file so that it starts with [string].\n" +
                                        "  -suffix [string]        :: rename the file so that it ends with [string]. \n" +
                                        "  -replace [str1] [str2]  :: rename [filename] by replacing all instances of [str1] with [str2]. \n" +
                                        "  -file [filename]        :: indicate the [filename] to be modified.");
                                System.exit(0);
                            } else {
                                if (num_of_arg == 0) {
                                    System.out.println("-prefix requires a string(minimum 1 argument), please provide a string and not an option");
                                    System.exit(0);
                                }
                                if (args[j].equals(file) || args[j].equals(suffix)
                                        || args[j].equals(replace)) {
                                    num_of_arg = 0;
                                    break;
                                }
                            }
                        }
                        pref = pref + args[j];
                        ++num_of_arg;
                    }
                }
                if (pref.length() != 0) {
                    if (pref.contains(date)) {
                        pref.replaceAll(date, localDate.toString());
                    }
                    if (pref.contains(time)) {
                        pref.replaceAll(time, localt.toString());
                    }
                }
                temp.add(pref);
                arguments.put(prefix, temp);
            }
            if (entry.equals(suffix)) {
                int index = suf_index;
                int num_of_arg = 0;
                String suff = new String("");
                int last_index = 0;
                if (index + 1 < args.length) {
                    for (int j = index + 1; j < args.length; ++j) {
                        if (args[j].startsWith("-")) {
                            if (args[j].equals(help)) {
                                System.out.println("(c) 2020 Jeff Avery. Last revised: Jan 2, 2020.\n" +
                                        " Usage: java rename [-option argument1 argument2 ...]\n" + "\n" +
                                        "  Options:\n" +
                                        "  -help                   :: display this help and exit.\n" +
                                        "  -prefix [string]        :: rename the file so that it starts with [string].\n" +
                                        "  -suffix [string]        :: rename the file so that it ends with [string]. \n" +
                                        "  -replace [str1] [str2]  :: rename [filename] by replacing all instances of [str1] with [str2]. \n" +
                                        "  -file [filename]        :: indicate the [filename] to be modified.");
                                System.exit(0);
                            } else {
                                if (num_of_arg == 0) {
                                    System.out.println("-suffix requires a string, please provide a string and not an option");
                                    System.exit(0);
                                }
                                if (args[j].equals(file) || args[j].equals(prefix)
                                        || args[j].equals(replace)) {
                                    break;
                                }
                            }
                        }
                        suff = suff + args[j];
                        ++num_of_arg;
                    }
                }
                if (suff.length() != 0) {
                    if (suff.contains(date)) {
                        suff.replaceAll(date, localDate.toString());
                    }
                    if (suff.contains(time)) {
                        suff.replaceAll(time, localt.toString());
                    }
                }
                temp1.add(suff);
                arguments.put(suffix, temp1);
            }
            if (entry.equals(replace)) {
                int index = rep_index;
                int last_index = 0;
                int number_of_arg = 0;
                for (int i = index + 1; i < args.length; ++i) {
                    if (args[i].startsWith("-")) {
                        if (args[i].equals(help)) {
                            System.out.println("(c) 2020 Jeff Avery. Last revised: Jan 2, 2020.\n" +
                                    " Usage: java rename [-option argument1 argument2 ...]\n" + "\n" +
                                    "  Options:\n" +
                                    "  -help                   :: display this help and exit.\n" +
                                    "  -prefix [string]        :: rename the file so that it starts with [string].\n" +
                                    "  -suffix [string]        :: rename the file so that it ends with [string]. \n" +
                                    "  -replace [str1] [str2]  :: rename [filename] by replacing all instances of [str1] with [str2]. \n" +
                                    "  -file [filename]        :: indicate the [filename] to be modified.");
                            System.exit(0);
                        } else {
                            if (number_of_arg != 2) {
                                System.out.println("Invalid option: replace requires exactly 2 arguments to be specified");
                                System.exit(0);
                            }
                            if (args[i].equals(file) || args[i].equals(suffix)
                                    || args[i].equals(prefix)) {
                                break;
                            }
                        }
                    } else {
                        if (args[i].length() != 0) {
                            if (args[i].contains(date)) {
                                args[i].replaceAll(date, localDate.toString());
                            }
                            if (args[i].contains(time)) {
                                args[i].replaceAll(time, localt.toString());
                            }
                        }
                        replace_val.add(args[i]);
                        ++number_of_arg;
                    }
                }
                if (replace_val.size() != 2) {
                    System.out.println("Invalid option: replace requires exactly 2 arguments to be specified");
                    System.exit(0);
                }
                arguments.put(replace, replace_val);
            }
            if (entry.equals(file)) {
                int index = findex;
                int number_of_arg = 0;
                if(args.length < 2){
                    System.out.println("no options provided");
                }
                if (index < args.length - 1) {
                    for (int j = index + 1; j < args.length; ++j) {
                        if (args[j].startsWith("-")) {
                            if (args[j].equals(help)) {
                                System.out.println("(c) 2020 Jeff Avery. Last revised: Jan 2, 2020.\n" +
                                        " Usage: java rename [-option argument1 argument2 ...]\n" + "\n" +
                                        "  Options:\n" +
                                        "  -help                   :: display this help and exit.\n" +
                                        "  -prefix [string]        :: rename the file so that it starts with [string].\n" +
                                        "  -suffix [string]        :: rename the file so that it ends with [string]. \n" +
                                        "  -replace [str1] [str2]  :: rename [filename] by replacing all instances of [str1] with [str2]. \n" +
                                        "  -file [filename]        :: indicate the [filename] to be modified.");
                                System.exit(0);
                            } else {
                                if (number_of_arg == 0) {
                                    System.out.println("-file requires a file name, please provide a file name and not an option");
                                    System.exit(0);
                                }
                                if (args[j].equals(prefix) || args[j].equals(suffix)
                                        || args[j].equals(replace)) {
                                    break;
                                }
                            }
                        }
                        File file1 = new File(args[j]);
                        if (!file1.exists()) {
                            System.out.println("Error: " + file1.toString() + " doesn't exists");
                            System.exit(0);
                        } else {
                            files_val.add(file1.toString());
                            arguments.put(file, files_val);
                            ++number_of_arg;
                        }
                    }
                }
            }
        }
        return arguments;
    }


    public static void main(String[] args) {
        boolean success = false;
        if (args.length == 0) {
            System.out.println("Usage: java rename [-option argument1 argument2 ..]");
            System.exit(0);
        }
        HashMap<String, Vector<String>> options = parse(args);
        for (Map.Entry<String, Vector<String>> entry : options.entrySet()) {
            String key = entry.getKey();
            if (key.equals(prefix)) {
                for (String f_entry : options.get(file)) {
                    File old = new File(f_entry);
                    System.out.print("java rename ");
                    System.out.print(f_entry + " -> ");
                    System.out.println(entry.getValue().elementAt(0) + f_entry );
                    File newf = new File(entry.getValue().elementAt(0) + f_entry);
                    int indexvec = options.get(file).indexOf(f_entry);
                    options.get(file).set(indexvec,entry.getValue().elementAt(0) + f_entry);
                    success = old.renameTo(newf);
                }
            }
            if (key.equals(suffix)) {
                for (String f_entry : options.get(file)) {
                    File old = new File(f_entry);
                    int index = f_entry.indexOf(".");
                    System.out.print("java rename " + f_entry + " -> " + f_entry.substring(0,index) +
                            entry.getValue().elementAt(0) + f_entry.substring(index,f_entry.length()));
                    int indexvec = options.get(file).indexOf(f_entry);
                    options.get(file).set(indexvec, f_entry.substring(0,index) + entry.getValue().elementAt(0) + f_entry.substring(index,f_entry.length()));
                    File newf = new File(f_entry.substring(0,index) + entry.getValue().elementAt(0) + f_entry.substring(index,f_entry.length()));
                    success = old.renameTo(newf);
                }
            }
            if (key.equals(replace)) {
                Vector<String> val = options.get(replace);
                for (String f_entry : options.get(file)) {
                    File old = new File(f_entry);
                    int index = f_entry.indexOf(".");
                    System.out.print("java rename " + f_entry + " -> " + f_entry.substring(0,index).replaceAll(val.elementAt(0), val.elementAt(1))
                    + f_entry.substring(index,f_entry.length()));
                    int indexvec = options.get(file).indexOf(f_entry);
                    options.get(file).set(indexvec,f_entry.substring(0,index).replaceAll(val.elementAt(0), val.elementAt(1))
                                    + f_entry.substring(index,f_entry.length()));
                    File newf = new File(f_entry.substring(0,index).replaceAll(val.elementAt(0), val.elementAt(1))
                            + f_entry.substring(index,f_entry.length()));
                    success = old.renameTo(newf);
                }
            }
            if(key.equals(file)){
                continue;
            }
        }
    }
}