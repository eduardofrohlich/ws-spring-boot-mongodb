package com.eduardofrohlich.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.eduardofrohlich.workshopmongo.domain.Post;
import com.eduardofrohlich.workshopmongo.domain.User;
import com.eduardofrohlich.workshopmongo.dto.AuthorDTO;
import com.eduardofrohlich.workshopmongo.dto.CommentDTO;
import com.eduardofrohlich.workshopmongo.repository.PostRepository;
import com.eduardofrohlich.workshopmongo.repository.UserRepository;


@Configuration
public class Instantiation implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();

		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob)); //salvar primeiro os usuarios para que tenham um id proprio
		//depois fazer a copia para o AuthorDTO, aí o ID vai vir corretamente (senao ele fica null)
	
		Post post1 = new Post(null, sdf.parse("21/02/2020"), "Partiu viagem", "To viajando pra Colombo!", new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("19/11/2022"),"Bom dia", "Uma ótima sexta a todos!", new AuthorDTO(maria));
		
		CommentDTO c1 = new CommentDTO("Boa viagem, tenha cuidado", sdf.parse("23/02/2020"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Ola, igualmente!", sdf.parse("21/11/2022"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Bom dia pra vcs tbm", sdf.parse("19/11/2022"), new AuthorDTO(alex));

		post1.getComments().addAll(Arrays.asList(c1));
		post2.getComments().addAll(Arrays.asList(c2, c3));
			
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		userRepository.save(maria);
	}

}
