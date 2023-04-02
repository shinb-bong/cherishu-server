package cherish.backend.common.dto;

import org.modelmapper.ModelMapper;

public class ModelMapperUtils {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static <D> D map(Object source, Class<D> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    public static void map(Object source, Object destination) {
        modelMapper.map(source, destination);
    }
}