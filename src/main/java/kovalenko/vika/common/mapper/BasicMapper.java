package kovalenko.vika.common.mapper;

public interface BasicMapper<E, D> {
    D toDTO(E entity);
}
