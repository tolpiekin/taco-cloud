package dev.tolpekin.tacos.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dev.tolpekin.tacos.data.IngredientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import dev.tolpekin.tacos.Ingredient;
import dev.tolpekin.tacos.Ingredient.Type;
import dev.tolpekin.tacos.Taco;
import dev.tolpekin.tacos.TacoOrder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

	private final IngredientRepository ingredientRepository;

	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}

	@ModelAttribute
	public void addIngredientsToModel(Model model) {
		Iterable<Ingredient> ingredients = ingredientRepository.findAll();
		Type[] types = Ingredient.Type.values();
		for(Type type: types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType((List<Ingredient>) ingredients, type));
		}
	 }
	
	 @ModelAttribute(name = "tacoOrder")
	 public TacoOrder order() {
		 return new TacoOrder();
	 }

	 @ModelAttribute(name = "taco")
	 public Taco taco() {
		 return new Taco();
	 }
	 
	 @GetMapping
	 public String showDesignForm() {
		 return "design";
	 }
	 
	 private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		 return ingredients
				 .stream()
				 .filter(x -> x.getType().equals(type))
				 .collect(Collectors.toList());
	 }

	@PostMapping
	public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {

		if (errors.hasErrors()) {
			return "design";
		}

		tacoOrder.addTaco(taco);
		log.info("Processing taco: {}", taco);

		return "redirect:/orders/current";
	}
}
