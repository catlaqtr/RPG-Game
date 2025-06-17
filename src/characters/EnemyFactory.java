package characters;

import java.util.Random;

public class EnemyFactory {

    private final Random random = new Random();

    public Enemy generate(int wave) {
        String[] names = {"Goblin", "Orc", "Skeleton", "Dark Elf", "Troll", "Demon"};
        String name = names[random.nextInt(names.length)];

        int baseHP = 30 + wave * 10;
        int baseDamage = 5 + wave * 2;

        // We'll use baseDamage logic later — for now, store it as HP
        return new Enemy(name + " (Wave " + wave + ")", baseHP, baseHP);
    }
}
