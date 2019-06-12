package com.daap.model;

import java.util.ArrayList;


@SuppressWarnings("serial")
public class Relations extends ArrayList<Relation> {

	/**
	 * Returns a filtered form of this object, depending on the input type.
	 *
	 * @param type Can be: {call}{create}{ref}{use}{inh}{has}{wildcard}
	 */
	public ArrayList<Relation> getRelationsByType(Relation.Type type) {
		ArrayList<Relation> filteredRelations = new ArrayList<Relation>();
		for (Relation relation : this) {
			if (relation.hasType(type))
				filteredRelations.add(relation);
		}
		return filteredRelations;
	}

	public Relations getFilteredRelationsByType(Relation.Type type) {
		Relations filteredRelations = new Relations();
		for (Relation relation : this) {
			if (relation.hasType(type))
				filteredRelations.add(relation);
		}
		return filteredRelations;
	}

	public Relations getFilteredRelationsByTypes(Relation.Type type1, Relation.Type type2) {
		Relations filteredRelations = new Relations();
		for (Relation relation : this) {
			if (relation.hasType(type1) || relation.hasType(type2))
				filteredRelations.add(relation);
		}
		return filteredRelations;
	}

	public Relation getRelationsByParentClass(String name) {
//		Relations filteredConnections = new Relations();
		for (Relation relation : this) {
			if (name.equals(relation.getTo().getName()))
				return  relation;
		}
		return null;
	}

    public Relations getFilterConnectionsByFrom(String name) {
        Relations filteredRelations = new Relations();
        for (Relation relation : this) {
            if (name.equals(relation.getFrom().getName()))
                filteredRelations.add(relation);
        }
        return filteredRelations;
    }

    public Relations getFilterRelationsByTo(String name) {
        Relations filteredRelations = new Relations();
        for (Relation relation : this) {
            if (name.equals(relation.getTo().getName()))
                filteredRelations.add(relation);
        }
        return filteredRelations;
    }

	public Relations getUniqueRelationsTo() {
		Relations filteredRelations = new Relations();
		for (Relation relation : this) {
			boolean exists = false;
			for (Relation relation1 : filteredRelations){
				if (relation.getTo().getName().equals(relation1.getTo().getName())){
					exists = true;
					break;
				}
			}
			if (!exists){
				filteredRelations.add(relation);
			}
		}
		return filteredRelations;
	}
	public Relations getUniqueRelationsFrom() {
		Relations filteredRelations = new Relations();
		for (Relation relation : this) {
			boolean exists = false;
			for (Relation relation1 : filteredRelations){
				if (relation.getFrom().getName().equals(relation1.getFrom().getName())){
					exists = true;
					break;
				}
			}
			if (!exists){
				filteredRelations.add(relation);
			}
		}
		return filteredRelations;
	}
}
