package hr.tvz.java.web.susac.joke;

import hr.tvz.java.web.susac.joke.dto.CategorySearchDTO;
import hr.tvz.java.web.susac.joke.dto.JokeDTO;
import hr.tvz.java.web.susac.joke.dto.JokeSearchDTO;
import hr.tvz.java.web.susac.joke.enums.JokeSortEnum;
import hr.tvz.java.web.susac.joke.model.Category;
import hr.tvz.java.web.susac.joke.model.Joke;
import hr.tvz.java.web.susac.joke.repository.CategoryRepository;

public class UtilStatic {

    public static final Long LONG_ID_ONE = Long.valueOf(1);
    public static final Long LONG_ID_SEVEN = Long.valueOf(7);
    public static final Long LONG_ID_ELEVEN = Long.valueOf(11);

    public static final String STRING_C_PLUS = "C++";
    public static final String STRING_CHUCK_NORRIS = "Chuck Norris";
    public static final String STRING_HELLO_WORLD = "Hello world!";
    public static final String STRING_NEW_JOKE = "New Joke";
    public static final String STRING_PRO = "Pro";
    public static final String STRING_PROGRAMMING = "Programming";

    public static final String URL_API_CATEGORY = "/api/category";
    public static final String URL_API_JOKE = "/api/joke";

    public static Category createCategory(){
        Category category = new Category();
        category.setTitle(STRING_HELLO_WORLD);

        return category;
    }

    public static CategorySearchDTO createCategorySearchDTO(){
        CategorySearchDTO categorySearchDTO = new CategorySearchDTO();
        categorySearchDTO.setTitle(STRING_PRO);

        return categorySearchDTO;
    }

    public static Joke createJoke(CategoryRepository categoryRepository){
        Category category = categoryRepository.findOneByTitle(STRING_PROGRAMMING).orElse(null);

        Joke joke = new Joke();
        joke.setDescription(STRING_PROGRAMMING);
        joke.setCategory(category);

        return joke;
    }

    public static JokeDTO createJokeDTO_withExistingCategory(){
        JokeDTO jokeDTO = new JokeDTO();
        jokeDTO.setCategoryTitle(STRING_PROGRAMMING);
        jokeDTO.setDescription(STRING_HELLO_WORLD);

        return jokeDTO;
    }

    public static JokeDTO createJokeDTO_withNewCategory(){
        JokeDTO jokeDTO = new JokeDTO();
        jokeDTO.setCategoryTitle(STRING_NEW_JOKE);
        jokeDTO.setDescription(STRING_HELLO_WORLD);

        return jokeDTO;
    }

    public static JokeDTO createJokeDTO_incomplete(){
        JokeDTO jokeDTO = new JokeDTO();
        jokeDTO.setCategoryTitle(STRING_NEW_JOKE);

        return jokeDTO;
    }

    public static JokeSearchDTO createJokeSearchDTO(){
        JokeSearchDTO jokeSearchDTO = new JokeSearchDTO();
        jokeSearchDTO.setCategoryTitle(STRING_PRO);
        jokeSearchDTO.setJokeSortEnum(JokeSortEnum.RELEVANT);

        return jokeSearchDTO;
    }

    public static JokeSearchDTO createSearchDTO_withNameNewJoke(){
        JokeSearchDTO jokeSearchDTO = new JokeSearchDTO();
        jokeSearchDTO.setCategoryTitle(STRING_NEW_JOKE);
        jokeSearchDTO.setJokeSortEnum(JokeSortEnum.RELEVANT);

        return jokeSearchDTO;
    }

    public static JokeSearchDTO createSearchDTO_withDate(){
        JokeSearchDTO jokeSearchDTO = new JokeSearchDTO();
        jokeSearchDTO.setCategoryTitle(STRING_PRO);
        jokeSearchDTO.setJokeSortEnum(JokeSortEnum.DATE_NEW);

        return jokeSearchDTO;
    }
}
