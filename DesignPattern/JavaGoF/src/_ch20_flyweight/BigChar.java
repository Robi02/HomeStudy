package _ch20_flyweight;

public class BigChar {
    
    private char charname;
    private String fontdata;

    public BigChar(char charname) {
        this.charname = charname;
        StringBuilder sb = new StringBuilder();

        switch (charname) {
            default:
            sb.append(charname).append('?');
            break;
            case '0':
            sb.append("   ____   \n")
              .append(" .'    '. \n")
              .append("|  .--.  |\n")
              .append("| |    | |\n")
              .append("|  `--'  |\n")
              .append(" '.____.' \n");
            break;
            case '1':
            sb.append("  __   \n")
              .append(" /  |  \n")
              .append(" `| |  \n")
              .append("  | |  \n")
              .append(" _| |_ \n")
              .append("|_____|\n");
            break;
            case '2':
            sb.append("  _____  \n")
              .append(" / ___ `.\n")
              .append("|_/___) |\n")
              .append(" .'____.'\n")
              .append("/ /_____ \n")
              .append("|_______|\n");
            break;
            case '3':
            sb.append("  ______  \n")
              .append(" / ____ `.\n")
              .append(" `'  __) |\n")
              .append(" _  |__ '.\n")
              .append("| \\____) |\n")
              .append(" \\______.'\n");
            break;
            case '4':
            sb.append(" _    _   \n")
              .append("| |  | |  \n")
              .append("| |__| |_ \n")
              .append("|____   _|\n")
              .append("    _| |_ \n")
              .append("   |_____|\n");
            break;
            case '5':
            sb.append(" _______  \n")
              .append("|  _____| \n")
              .append("| |____   \n")
              .append("'_.____''.\n")
              .append("| \\____) |\n")
              .append(" \\______.'\n");
            break;
            case '6':
            sb.append(" ______   \n")
              .append(".' ____ \\ \n")
              .append("| |____\\_|\n")
              .append("| '____`'.\n")
              .append("| (____) |\n")
              .append("'.______.'\n");
            break;
            case '7':
            sb.append(" _______ \n")
              .append("|  ___  |\n")
              .append("|_/  / / \n")
              .append("    / /  \n")
              .append("   / /   \n")
              .append("  /_/    \n");
            break;
            case '8':
            sb.append("   ____   \n")
              .append(" .' __ '. \n")
              .append(" | (__) | \n")
              .append(" .`____'. \n")
              .append("| (____) |\n")
              .append("`.______.'\n");
            break;
            case '9':
            sb.append("  ______  \n")
              .append(".' ____ '.\n")
              .append("| (____) |\n")
              .append("'_.____. |\n")
              .append("| \\____| |\n")
              .append(" \\______,'\n");
            break;
            case '-':
            sb.append("            \n")
              .append("   ______   \n")
              .append("  |______|  \n")
              .append("            \n")
              .append("            \n")
              .append("            \n");
            break;
        }

        this.fontdata = sb.toString();
    }

    public void print() {
        System.out.println(fontdata);
    }
}
