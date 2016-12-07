package org.crayonpedia.yoopa.hub.twitter;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ceefour on 07/12/2016.
 */
public interface TwitterAuthzRepository extends JpaRepository<TwitterAuthz, Integer> {
}
