package edu.obya.blueprint.common.infra.data.jpa;

import edu.obya.blueprint.common.util.search.FindCriteria;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpecificationBuilder<E> {

    private final List<Specification<E>> params = new ArrayList<>();

    public SpecificationBuilder<E> with(FindCriteria criteria) {
        params.add(SpecificationFactory.from(criteria));
        return this;
    }

    public Optional<Specification<E>> build() {
        return params.stream().reduce(Specification::and);
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class SpecificationFactory {

        public static <E> Specification<E> from(FindCriteria criteria) {
            return (root, query, builder) -> {
                switch (criteria.getOperation()) {
                    case ">" -> {
                        return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
                    }
                    case "<" -> {
                        return builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
                    }
                    case ":" -> {
                        return (String.class == root.get(criteria.getKey()).getJavaType()) ?
                            builder.like(root.get(criteria.getKey()), criteria.getValue() + "%") :
                            builder.equal(root.get(criteria.getKey()), criteria.getValue());
                    }
                    default -> {
                        return null;
                    }
                }
            };
        }
    }

}
