package dev.tolpekin.tacos.data;

import dev.tolpekin.tacos.Ingredient;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
