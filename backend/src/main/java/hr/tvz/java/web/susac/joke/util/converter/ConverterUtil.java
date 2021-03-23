package hr.tvz.java.web.susac.joke.util.converter;

public interface ConverterUtil<E, D> {
    D convertToDTO(E entity);
    E convertToEntity(D dto);
}
