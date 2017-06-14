package team.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


import team.entity.Candidato;
import team.entity.Voto;

public class VotoDaoImpl implements VotoDao {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public VotoDaoImpl() {
		emf = Persistence.createEntityManagerFactory("sql10179092");
		em = emf.createEntityManager();
	}
	
	@Override
	public void save(Voto voto) {
		em.getTransaction().begin();
		em.persist(voto);
		em.getTransaction().commit();
		em.clear();
		em.close();
		emf.close();
	}

	@Override
	public List<Voto> getAllVotos() {
		em = emf.createEntityManager();
		TypedQuery<Voto> qry = em.createQuery("select o from Voto o", Voto.class);
		List<Voto> votos = qry.getResultList();
//		em.close();
//		emf.close();
		return votos;
	}

	@Override
	public long contaVotos(Candidato candidato) {
        Query query = em.createQuery("SELECT count(*) FROM Voto WHERE candidatoEscolhido.id =:id");
        query.setParameter("id", candidato.getId());
        long count = (long) query.getSingleResult();
//       em.close();
//		emf.close();
        return count;
	}

	@Override
	public void removeVotos(long candidatoId) {
		em.getTransaction().begin();
		Query query = em.createQuery("DELETE from Voto WHERE candidatoEscolhido.id =:id");
		query.setParameter("id", candidatoId);
		query.executeUpdate();
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	@Override
	public long contaVotoPorEstados(String estado) {
		Query query = em.createQuery("SELECT count(*) FROM Voto WHERE estado =:estado");
        query.setParameter("estado", estado);
        long count = (long) query.getSingleResult();
//       em.close();
//		emf.close();
        return count;
	}

}
