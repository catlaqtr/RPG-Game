package characters;


    public class Enemy {
        private String name;
        private int health;
        private int maxHealth;


        public Enemy(String name, int health, int maxHealth) {
            this.name = name;
            this.health = maxHealth;
            this.maxHealth = maxHealth;

        }

        public int attack() {
            // Return random damage between 5 and 10
            return (int)(Math.random() * 6) + 5; // 0–5 → +5 = 5–10
        }




        public void takeDamage(int amount) {
            health -= amount;
            if (health < 0) health = 0;
        }


        public boolean isAlive(){
            return health > 0;
        }

        public String getName() {
            return name;
        }

        public int getHealth() {
            return health;
        }

        public int getMaxHealth() {
            return maxHealth;
        }


    }

