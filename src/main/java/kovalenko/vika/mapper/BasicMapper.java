package kovalenko.vika.mapper;

public interface BasicMapper<E, D> {
    D toDTO(E entity);
}
