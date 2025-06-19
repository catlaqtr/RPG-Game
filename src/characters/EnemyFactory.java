package characters;

import java.util.Random;

public class EnemyFactory {
    private final Random random = new Random();

    public Enemy generate(int wave) {
        String[] names = {"Goblin", "Orc", "Skeleton", "Dark Elf", "Troll", "Demon"};
        String name = names[random.nextInt(names.length)];

        int baseHP = 30 + wave * 10;
        int baseAttackMin = 5 + wave;
        int baseAttackMax = baseAttackMin + 5;

        return new Enemy(name + " (Wave " + wave + ")", baseHP, baseHP, baseAttackMin, baseAttackMax);
    }
}