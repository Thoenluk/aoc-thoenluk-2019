package adventofcode2019;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Lukas Thöni <Lukas.thoeni@gmx.ch>
 */
public class Chemical {

    private int amountProduced;
    private long stock;
    private final LinkedList<Chemical> requiredChems;
    private final HashMap<Chemical, Long> recipe;
    private final String name;

    public Chemical(String name) {
        this.stock = 0;
        this.requiredChems = new LinkedList<>();
        this.recipe = new HashMap<>();
        this.name = name;
    }

    public void addRequired(Chemical requirement, long amount) {
        requiredChems.add(requirement);
        recipe.put(requirement, amount);
    }

    public String recipeAsString() {
        StringWriter writer = new StringWriter();
        Chemical requirement;
        for (int i = 0; i < requiredChems.size(); i++) {
            requirement = requiredChems.get(i);
            writer.write(recipe.get(requirement).toString());
            writer.write(" ");
            writer.write(requirement.getName());
            if (i < requiredChems.size() - 1) {
                writer.write(",");
            }
            writer.write(" ");
        }
        writer.write("=> ");
        writer.write(String.valueOf(this.getAmountProduced()));
        writer.write(" ");
        writer.write(this.getName());
        return writer.toString();
    }

    public HashMap<Chemical, Long> produce() {
        HashMap<Chemical, Long> costs = new HashMap<>();
        HashMap<Chemical, Long> inheritedCosts;
        for (Chemical requirement : requiredChems) {
            while (requirement.stock < recipe.get(requirement)) {
                inheritedCosts = requirement.produce();
                for (Chemical chem : inheritedCosts.keySet()) {
                    if (!costs.containsKey(chem)) {
                        costs.put(chem, (long) 0);
                    }
                    costs.put(chem, costs.get(chem) + inheritedCosts.get(chem));
                }
            }
            if (!costs.containsKey(requirement)) {
                costs.put(requirement, (long) 0);
            }
            costs.put(requirement, costs.get(requirement) + recipe.get(requirement));
            requirement.setStock(requirement.getStock() - recipe.get(requirement));
        }
        stock += amountProduced;
        return costs;
    }

    public long getRequiredAmount(Chemical chem) {
        return recipe.get(chem);
    }

    public void setAmountProduced(int amountProduced) {
        this.amountProduced = amountProduced;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public int getAmountProduced() {
        return amountProduced;
    }

    public long getStock() {
        return stock;
    }

    public LinkedList<Chemical> getRequiredChems() {
        return requiredChems;
    }

    public String getName() {
        return name;
    }

}
