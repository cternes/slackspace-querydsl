package de.slackspace.tutorials.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import de.slackspace.tutorials.entity.Person;
import de.slackspace.tutorials.entity.Person_;
import de.slackspace.tutorials.entity.QPerson;

@Repository
public class PersonDaoImpl implements PersonDao {

	@PersistenceContext
	private EntityManager em;
	
	public Person save(Person person) {
		em.persist(person);
		return person;
	} 
	
	public List<Person> findPersonsByFirstnameJPA(String firstname) {
		CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
		
		CriteriaQuery<Person> criteriaQuery = queryBuilder.createQuery(Person.class);
		Root<Person> rootQuery = criteriaQuery.from(Person.class);
		
		CriteriaQuery<Person> select = criteriaQuery.select(rootQuery);
		
		Predicate p = queryBuilder.equal(rootQuery.get(Person_.firstname), firstname);
		select.where(p);
		
		TypedQuery<Person> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
	
	public List<Person> findPersonsByFirstnameQueryDSL(String firstname) {
		JPAQuery query = new JPAQuery(em);
		QPerson person = QPerson.person;
		
		return query.from(person).where(person.firstname.eq(firstname)).list(person);
	}
		
}
