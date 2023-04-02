package edu.obya.blueprint.common.infra.data.jpa;

import edu.obya.blueprint.common.util.search.FindCriteria;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SpecificationBuilder<E> {

    private final List<Specification<E>> params;

    public static <E> SpecificationBuilder<E> from(List<FindCriteria> criteriaList) {
        return new SpecificationBuilder<E>(
            criteriaList
                .stream()
                .map(FindCriteriaSpecification::<E>from)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList()
        );
    }

    public Specification<E> build() {
        return params.stream()
                .reduce(Specification::and)
                .orElse(null);
    }

    private static class FindCriteriaSpecification {

        private FindCriteriaSpecification() {
        }

        public static <E> Optional<Specification<E>> from(FindCriteria criteria) {
            return Optional.ofNullable(toSpec(criteria));
        }

        private static <E> Specification<E> toSpec(FindCriteria criteria) {
            return (root, query, builder) -> {
                if (criteria.getOperation().equalsIgnoreCase(">")) {
                    return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
                }
                else if (criteria.getOperation().equalsIgnoreCase("<")) {
                    return builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
                }
                else if (criteria.getOperation().equalsIgnoreCase(":")) {
                    if (root.get(criteria.getKey()).getJavaType() == String.class) {
                        return builder.like(root.get(criteria.getKey()), criteria.getValue() + "%");
                    } else {
                        return builder.equal(root.get(criteria.getKey()), criteria.getValue());
                    }
                }
                return null;
            };
        }
    }

}
