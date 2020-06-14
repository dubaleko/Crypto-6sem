import java.util.*;

public class Main {

    public static List<Character> alphabet = new ArrayList<Character>(){{
        add('a'); add('b'); add('c'); add('d'); add('e'); add('f'); add('g'); add('h'); add('i');
        add('j'); add('k'); add('l'); add('m'); add('n'); add('o'); add('p'); add('q'); add('r');
        add('s'); add('t'); add('u'); add('v'); add('w'); add('x'); add('y'); add('z');
    }};

    public static List<Character> routerL = new ArrayList<Character>(){{
        add('b'); add('d'); add('f'); add('h'); add('j'); add('l'); add('c'); add('p'); add('r');
        add('t'); add('x'); add('v'); add('z'); add('n'); add('y'); add('e'); add('i'); add('w');
        add('g'); add('a'); add('k'); add('m'); add('u'); add('s'); add('q'); add('o');
    }};

    public static List<Character> routerM = new ArrayList<Character>(){{
        add('n'); add('z'); add('j'); add('h'); add('g'); add('r'); add('c'); add('x'); add('m');
        add('y'); add('s'); add('w'); add('b'); add('o'); add('u'); add('f'); add('a'); add('i');
        add('v'); add('l'); add('p'); add('e'); add('k'); add('q'); add('d'); add('t');
    }};

    public static List<Character> routerR = new ArrayList<Character>(){{
        add('e'); add('k'); add('m'); add('f'); add('l'); add('g'); add('d'); add('q'); add('v');
        add('z'); add('n'); add('t'); add('o'); add('w'); add('y'); add('h'); add('x'); add('u');
        add('s'); add('p'); add('a'); add('i'); add('b'); add('r'); add('c'); add('j');
    }};

    public static Map<Character,Character> Reflector = new HashMap<Character, Character>(){{
        put('a','e');put('b','n');put('c','k');put('d','q');put('f','u');put('g','y');put('h','w');
        put('i','j'); put('l','o');put('m','p');put('r','x');put('s','z');put('t','v');
    }};

    public static int getRouterPos(){
        int routerPos;
        Scanner scannerR = new Scanner(System.in);
        if (scannerR.hasNextInt()) {
            routerPos  = scannerR.nextInt();
        }
        else {
            while (scannerR.hasNextInt() != true){
                System.out.println("Pls enter integer number");
                scannerR = new Scanner(System.in);
            }
            routerPos  = scannerR.nextInt();
        }
        return routerPos;
    }

    public static Character getCharacter(List<Character> first, List<Character> second, Character character, int routerPos ){
        return  second.get(first.indexOf(character));
    }

    public static Character getCharacterFromMap(Character character){
        Iterator it = Reflector.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if (character == pair.getKey()){
                character = (Character) pair.getValue();
            }
            else if (character == pair.getValue()){
                character = (Character) pair.getKey();
            }
        }
        return character;
    }

    public  static List<Character> setRouter(List<Character> router, int position ){
        List<Character> timeRouterR = new ArrayList<Character>();
        for (int i =position-1; i<26; i++){
            timeRouterR.add(router.get(i));
        }
        for (int i = 0; i < position-1; i++){
            timeRouterR.add(router.get(i));
        }
        return  timeRouterR;
    }

    public static void main(String[] args) {
        int rightRouterPos = 1;
        int middleRouterPos = 1;
        int leftRouterPos = 1;
        int rigthtRouterAll = 0;

        System.out.println("Set start position for Right router: ");
        rightRouterPos = getRouterPos();
        System.out.println("Set start position for Middle router: ");
        middleRouterPos = getRouterPos();
        System.out.println("Set start position for Left router: ");
        leftRouterPos = getRouterPos();

        List<Character> timeRouterR = setRouter(routerR,rightRouterPos);
        List<Character> timeRouterM = setRouter(routerM,middleRouterPos);
        List<Character> timeRouterL = setRouter(routerL,leftRouterPos);

        System.out.println("Enter letter: ");
        Scanner scannerR = new Scanner(System.in);
        char[] messageArray =  scannerR.next().toCharArray();

        for (int i = 0; i<messageArray.length; i++){
            Character character = messageArray[i];

            character = getCharacter(alphabet,timeRouterR,character,rightRouterPos);
            character = getCharacter(alphabet,timeRouterM,character,middleRouterPos);
            character = getCharacter(alphabet,timeRouterL,character,leftRouterPos);

            character = getCharacterFromMap(character);

            character = getCharacter(timeRouterL,alphabet,character,rightRouterPos);
            character = getCharacter(timeRouterM,alphabet,character,middleRouterPos);
            character = getCharacter(timeRouterR,alphabet,character,leftRouterPos);

            rigthtRouterAll++;
            if (rigthtRouterAll == 25){
                rigthtRouterAll = 0;
                timeRouterM = setRouter(timeRouterM,2);
            }
            timeRouterR = setRouter(timeRouterR,2);
            timeRouterL = setRouter(timeRouterL,2);

            messageArray[i] = character;
        }
        String finalResult = "";
        for (int i=0;i<messageArray.length;i++){
            finalResult = finalResult + messageArray[i];
        }
        System.out.println("Enigma encrypted message: "+finalResult);
    }
}
