package team.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;


import team.entity.Candidato;

public class CandidatoDaoImp implements CandidatoDao{
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public CandidatoDaoImp() {
		emf = Persistence.createEntityManagerFactory("sql10179092");
		em = emf.createEntityManager();
	}
	
	@Override
	public void save(Candidato candidato) {
		Candidato c = em.merge(candidato);
		em.getTransaction().begin();
		em.persist( c );
		em.getTransaction().commit();
		em.clear();
		em.close();
		emf.close();
	}

	@Override
	public Candidato getCandidato(long id) {
		Candidato candidato = em.getReference(Candidato.class, id);
		em.clear();
//		em.close();
//		emf.close();
		return candidato;
	}

	@Override
	public List<Candidato> readMany() {
		TypedQuery<Candidato> query = em.createQuery("select o from Candidato o", 
				Candidato.class);
		List<Candidato> candidatos = query.getResultList();
		System.out.println("tamanho da lista: " + candidatos.size());
		em.clear();
		
//		em.close();
//		emf.close();
		return candidatos;
	}
	
	@Override
	public Candidato read(String nome) {
		Candidato candidato = (Candidato) em.createQuery("SELECT c FROM Candidato c WHERE c.nome = :nome")
			    .setParameter("nome", nome)
			    .setMaxResults(1)
			    .getResultList()
			    .get(0);
		em.clear();
		em.close();
		emf.close();
		return candidato;
	}

	@Override
	public void remove(Candidato candidato) {
		Candidato c = em.merge(candidato);
		em.getTransaction().begin();
		em.remove( c );
		em.getTransaction().commit();
		em.clear();
		em.close();
		emf.close();
	}

	@Override
	public void update(Candidato candidato) {
		em.getTransaction().begin();
		em.merge( candidato );
		em.getTransaction().commit();
		em.clear();
		em.close();
		emf.close();
	}



}
