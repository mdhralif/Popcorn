package com.kaankaplan.movieService.config;

import com.kaankaplan.movieService.dao.*;
import com.kaankaplan.movieService.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final CategoryDao categoryDao;
    private final DirectorDao directorDao;
    private final CityDao cityDao;
    private final SaloonDao saloonDao;
    private final MovieDao movieDao;
    private final ActorDao actorDao;
    private final CommentDao commentDao;
    private final MovieSaloonTimeDao movieSaloonTimeDao;
    private final MovieImageDao movieImageDao;

    @Override
    public void run(String... args) throws Exception {
        initializeCategories();
        initializeDirectors();
        initializeCities();
        initializeSaloons();
        initializeMovies();
        initializeMovieImages();
        initializeActors();
        initializeComments();
        initializeMovieSaloonTimes();
        printSummary();
    }

    private void initializeCategories() {
        try {
            if (categoryDao.count() == 0) {
                log.info("Initializing categories...");
                
                List<String> categoryNames = Arrays.asList(
                    "Action", "Drama", "Comedy", "Horror", "Sci-Fi",
                    "Romance", "Thriller", "Adventure", "Animation", "Documentary"
                );

                for (String categoryName : categoryNames) {
                    Category category = new Category();
                    category.setCategoryName(categoryName);
                    categoryDao.save(category);
                }
                
                log.info("✅ Categories initialized: {} records", categoryNames.size());
            } else {
                log.info("Categories already exist, skipping initialization");
            }
        } catch (Exception e) {
            log.error("Error initializing categories: ", e);
        }
    }

    private void initializeDirectors() {
        try {
            if (directorDao.count() == 0) {
                log.info("Initializing directors...");
                
                List<String> directorNames = Arrays.asList(
                    "Christopher Nolan", "Steven Spielberg", "Quentin Tarantino",
                    "Martin Scorsese", "James Cameron", "Alfred Hitchcock",
                    "Francis Ford Coppola", "Ridley Scott", "Tim Burton", "David Fincher"
                );

                for (String directorName : directorNames) {
                    Director director = Director.builder()
                        .directorName(directorName)
                        .build();
                    directorDao.save(director);
                }
                
                log.info("✅ Directors initialized: {} records", directorNames.size());
            } else {
                log.info("Directors already exist, skipping initialization");
            }
        } catch (Exception e) {
            log.error("Error initializing directors: ", e);
        }
    }

    private void initializeCities() {
        try {
            if (cityDao.count() == 0) {
                log.info("Initializing cities...");
                
                List<String> cityNames = Arrays.asList(
                    "New York", "Los Angeles", "Chicago", "Houston", "Phoenix",
                    "Philadelphia", "San Antonio", "San Diego", "Dallas", "San Jose",
                    "Austin", "Jacksonville", "Fort Worth", "Columbus", "Charlotte"
                );

                List<Movie> movies = movieDao.findAll();
                if (!movies.isEmpty()) {
                    // Create cities for each movie
                    for (Movie movie : movies) {
                        // Each movie gets 3-5 cities randomly
                        int cityCount = 3 + (int)(Math.random() * 3); // 3-5 cities per movie
                        for (int i = 0; i < cityCount; i++) {
                            String cityName = cityNames.get(i % cityNames.size());
                            City city = City.builder()
                                .cityName(cityName)
                                .movie(movie)
                                .build();
                            cityDao.save(city);
                        }
                    }
                    
                    log.info("✅ Cities initialized: {} records for {} movies", cityDao.count(), movies.size());
                } else {
                    log.warn("No movies found, skipping city initialization");
                }
            } else {
                log.info("Cities already exist, skipping initialization");
            }
        } catch (Exception e) {
            log.error("Error initializing cities: ", e);
        }
    }

    private void initializeSaloons() {
        try {
            if (saloonDao.count() == 0) {
                log.info("Initializing saloons...");
                
                List<String> saloonNames = Arrays.asList(
                    "Hall A", "Hall B", "Hall C", "IMAX Theater", "Premium Hall",
                    "VIP Theater", "3D Theater", "Standard Hall 1", "Standard Hall 2", "Standard Hall 3"
                );

                List<City> cities = cityDao.findAll();
                if (!cities.isEmpty()) {
                    City defaultCity = cities.get(0); // Use first city for all saloons

                    for (String saloonName : saloonNames) {
                        Saloon saloon = new Saloon();
                        saloon.setSaloonName(saloonName);
                        saloon.setCity(defaultCity);
                        saloonDao.save(saloon);
                    }
                    
                    log.info("✅ Saloons initialized: {} records", saloonNames.size());
                } else {
                    log.warn("No cities found, skipping saloon initialization");
                }
            } else {
                log.info("Saloons already exist, skipping initialization");
            }
        } catch (Exception e) {
            log.error("Error initializing saloons: ", e);
        }
    }

    private void initializeMovies() {
        try {
            if (movieDao.count() == 0) {
                log.info("Initializing movies...");
                
                List<Category> categories = categoryDao.findAll();
                List<Director> directors = directorDao.findAll();

                if (!categories.isEmpty() && !directors.isEmpty()) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    // Movie 1: Inception
                    Movie inception = Movie.builder()
                        .movieName("Inception")
                        .description("A skilled thief is given a chance at redemption if he can successfully perform an inception.")
                        .duration(148)
                        .releaseDate(dateFormat.parse("2010-07-16"))
                        .isDisplay(true)
                        .movieTrailerUrl("https://www.youtube.com/watch?v=YoHD9XEInc0")
                        .category(categories.get(0)) // Action
                        .director(directors.get(0)) // Christopher Nolan
                        .build();
                    movieDao.save(inception);

                    // Movie 2: The Dark Knight
                    Movie darkKnight = Movie.builder()
                        .movieName("The Dark Knight")
                        .description("When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests.")
                        .duration(152)
                        .releaseDate(dateFormat.parse("2008-07-18"))
                        .isDisplay(true)
                        .movieTrailerUrl("https://www.youtube.com/watch?v=EXeTwQWrcwY")
                        .category(categories.get(0)) // Action
                        .director(directors.get(0)) // Christopher Nolan
                        .build();
                    movieDao.save(darkKnight);

                    // Movie 3: Pulp Fiction
                    Movie pulpFiction = Movie.builder()
                        .movieName("Pulp Fiction")
                        .description("The lives of two mob hitmen, a boxer, a gangster and his wife intertwine in four tales of violence and redemption.")
                        .duration(154)
                        .releaseDate(dateFormat.parse("1994-10-14"))
                        .isDisplay(true)
                        .movieTrailerUrl("https://www.youtube.com/watch?v=s7EdQ4FqbhY")
                        .category(categories.size() > 1 ? categories.get(1) : categories.get(0)) // Drama
                        .director(directors.size() > 2 ? directors.get(2) : directors.get(0)) // Quentin Tarantino
                        .build();
                    movieDao.save(pulpFiction);

                    // Movie 4: Avatar (Coming Soon)
                    Movie avatar = Movie.builder()
                        .movieName("Avatar")
                        .description("A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.")
                        .duration(162)
                        .releaseDate(dateFormat.parse("2009-12-18"))
                        .isDisplay(false) // Coming soon
                        .movieTrailerUrl("https://www.youtube.com/watch?v=5PSNL1qE6VY")
                        .category(categories.size() > 4 ? categories.get(4) : categories.get(0)) // Sci-Fi
                        .director(directors.size() > 4 ? directors.get(4) : directors.get(0)) // James Cameron
                        .build();
                    movieDao.save(avatar);

                    // Movie 5: Goodfellas
                    Movie goodfellas = Movie.builder()
                        .movieName("Goodfellas")
                        .description("The story of Henry Hill and his life in the mob, covering his relationship with his wife Karen Hill and his mob partners.")
                        .duration(146)
                        .releaseDate(dateFormat.parse("1990-09-21"))
                        .isDisplay(true)
                        .movieTrailerUrl("https://www.youtube.com/watch?v=qo5jJpHtI40")
                        .category(categories.size() > 1 ? categories.get(1) : categories.get(0)) // Drama
                        .director(directors.size() > 3 ? directors.get(3) : directors.get(0)) // Martin Scorsese
                        .build();
                    movieDao.save(goodfellas);

                    log.info("✅ Movies initialized: 5 records");
                } else {
                    log.warn("Categories or Directors not found, skipping movie initialization");
                }
            } else {
                log.info("Movies already exist, skipping initialization");
            }
        } catch (Exception e) {
            log.error("Error initializing movies: ", e);
        }
    }

    private void initializeMovieImages() {
        try {
            if (movieImageDao.count() == 0) {
                log.info("Initializing movie images...");
                
                List<Movie> movies = movieDao.findAll();
                
                if (!movies.isEmpty()) {
                    // Professional movie poster URLs
                    String[] imageUrls = {
                        "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_SX300.jpg", // Inception
                        "https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_SX300.jpg", // The Dark Knight
                        "https://m.media-amazon.com/images/M/MV5BNGNhMDIzZTUtNTBlZi00MTRlLWFjM2ItYzViMjE3YzI5MjljXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg", // Pulp Fiction
                        "https://m.media-amazon.com/images/M/MV5BZDA0OGQxNTItMDZkMC00N2UyLTg3MzMtYTJmNjg3Nzk5MzRiXkEyXkFqcGdeQXVyMjUzOTY1NTc@._V1_SX300.jpg", // Avatar
                        "https://m.media-amazon.com/images/M/MV5BYTViNzMxZjEtZGEwNy00MDNiLWIzNGQtZDY2MjQ1OWViZjFmXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg", // Goodfellas
                        "https://m.media-amazon.com/images/M/MV5BNzQzOTk3OTAtNDQ0Zi00ZTVkLWI0MTEtMDllZjNkYzNjNTc4L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg", // The Shawshank Redemption
                        "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg", // The Godfather
                        "https://m.media-amazon.com/images/M/MV5BNzQzOTk3OTAtNDQ0Zi00ZTVkLWI0MTEtMDllZjNkYzNjNTc4L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg", // The Shawshank Redemption
                        "https://m.media-amazon.com/images/M/MV5BNzVkYzIwMzItYjU0OC00MWI3LWI1YjctMzc0NDkwMDkxNTdiXkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_SX300.jpg", // The Matrix
                        "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg", // The Godfather
                        "https://m.media-amazon.com/images/M/MV5BNWIwODRlZTUtY2U3ZS00Yzg1LWJhNzYtMmZiYmEyNmU1NjMzXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg", // Forrest Gump
                        "https://m.media-amazon.com/images/M/MV5BMWU4N2FjNzYtNTVkNC00NzQ0LTg0MjAtYTJlMjFhNGUxZDFmXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg"  // Fight Club
                    };

                    // Create images for all movies, cycling through the image URLs if needed
                    for (int i = 0; i < movies.size(); i++) {
                        MovieImage movieImage = new MovieImage();
                        movieImage.setImageUrl(imageUrls[i % imageUrls.length]); // Cycle through images
                        movieImage.setMovie(movies.get(i));
                        movieImageDao.save(movieImage);
                    }

                    log.info("✅ Movie images initialized: {} records", movies.size());
                } else {
                    log.warn("No movies found, skipping movie image initialization");
                }
            } else {
                log.info("Movie images already exist, skipping initialization");
            }
        } catch (Exception e) {
            log.error("Error initializing movie images: ", e);
        }
    }

    private void initializeActors() {
        try {
            if (actorDao.count() == 0) {
                log.info("Initializing actors...");
                
                List<Movie> movies = movieDao.findAll();

                if (!movies.isEmpty()) {
                    // Actors for each movie
                    String[][] movieActors = {
                        {"Leonardo DiCaprio", "Marion Cotillard", "Tom Hardy", "Ellen Page", "Ken Watanabe"}, // Inception
                        {"Christian Bale", "Heath Ledger", "Aaron Eckhart", "Maggie Gyllenhaal", "Gary Oldman"}, // Dark Knight
                        {"John Travolta", "Samuel L. Jackson", "Uma Thurman", "Bruce Willis", "Ving Rhames"}, // Pulp Fiction
                        {"Sam Worthington", "Zoe Saldana", "Sigourney Weaver", "Stephen Lang", "Michelle Rodriguez"}, // Avatar
                        {"Robert De Niro", "Ray Liotta", "Joe Pesci", "Lorraine Bracco", "Paul Sorvino"} // Goodfellas
                    };

                    int actorCount = 0;
                    for (int i = 0; i < Math.min(movies.size(), movieActors.length); i++) {
                        Movie movie = movies.get(i);
                        for (String actorName : movieActors[i]) {
                            Actor actor = Actor.builder()
                                .actorName(actorName)
                                .movie(movie)
                                .build();
                            actorDao.save(actor);
                            actorCount++;
                        }
                    }

                    log.info("✅ Actors initialized: {} records", actorCount);
                } else {
                    log.warn("No movies found, skipping actor initialization");
                }
            } else {
                log.info("Actors already exist, skipping initialization");
            }
        } catch (Exception e) {
            log.error("Error initializing actors: ", e);
        }
    }

    private void initializeComments() {
        try {
            if (commentDao.count() == 0) {
                log.info("Initializing comments...");
                
                List<Movie> movies = movieDao.findAll();

                if (!movies.isEmpty()) {
                    String[][] comments = {
                        {"Amazing movie! Christopher Nolan at his best.", "john.doe@example.com", "John Doe"},
                        {"Mind-bending plot and excellent cinematography.", "jane.smith@example.com", "Jane Smith"},
                        {"Heath Ledger's performance as Joker is legendary.", "movie.lover@example.com", "Movie Lover"},
                        {"One of the best superhero movies ever made.", "batman.fan@example.com", "Batman Fan"},
                        {"Tarantino's masterpiece with incredible dialogue.", "film.critic@example.com", "Film Critic"},
                        {"Visual effects are groundbreaking.", "scifi.fan@example.com", "SciFi Fan"},
                        {"Classic gangster movie with great acting.", "classic.movies@example.com", "Classic Fan"}
                    };

                    for (int i = 0; i < Math.min(comments.length, movies.size() * 2); i++) {
                        Comment comment = Comment.builder()
                            .commentText(comments[i % comments.length][0])
                            .commentByUserId(comments[i % comments.length][1])
                            .commentBy(comments[i % comments.length][2])
                            .movie(movies.get(i % movies.size()))
                            .build();
                        commentDao.save(comment);
                    }

                    log.info("✅ Comments initialized: {} records", Math.min(comments.length, movies.size() * 2));
                } else {
                    log.warn("No movies found, skipping comment initialization");
                }
            } else {
                log.info("Comments already exist, skipping initialization");
            }
        } catch (Exception e) {
            log.error("Error initializing comments: ", e);
        }
    }

    private void initializeMovieSaloonTimes() {
        try {
            if (movieSaloonTimeDao.count() == 0) {
                log.info("Initializing movie saloon times...");
                
                List<Movie> movies = movieDao.findAll();
                List<Saloon> saloons = saloonDao.findAll();

                if (!movies.isEmpty() && !saloons.isEmpty()) {
                    String[] showtimes = {"10:00", "13:30", "16:45", "20:00", "22:30"};
                    int showtimeCount = 0;

                    // Create showtimes for movies that are currently displaying
                    for (Movie movie : movies) {
                        if (movie.isDisplay()) { // Only for currently showing movies
                            for (int i = 0; i < Math.min(3, saloons.size()); i++) { // Max 3 saloons per movie
                                for (String showtime : showtimes) {
                                    MovieSaloonTime movieSaloonTime = new MovieSaloonTime();
                                    movieSaloonTime.setMovieBeginTime(showtime);
                                    movieSaloonTime.setMovie(movie);
                                    movieSaloonTime.setSaloon(saloons.get(i));
                                    movieSaloonTimeDao.save(movieSaloonTime);
                                    showtimeCount++;
                                }
                            }
                        }
                    }

                    log.info("✅ Movie Saloon Times initialized: {} records", showtimeCount);
                } else {
                    log.warn("Movies or Saloons not found, skipping showtime initialization");
                }
            } else {
                log.info("Movie Saloon Times already exist, skipping initialization");
            }
        } catch (Exception e) {
            log.error("Error initializing movie saloon times: ", e);
        }
    }

    private void printSummary() {
        log.info("🎬 Data initialization completed successfully!");
        log.info("📊 Database Summary:");
        log.info("   - Categories: {}", categoryDao.count());
        log.info("   - Directors: {}", directorDao.count());
        log.info("   - Cities: {}", cityDao.count());
        log.info("   - Saloons: {}", saloonDao.count());
        log.info("   - Movies: {}", movieDao.count());
        log.info("   - Movie Images: {}", movieImageDao.count());
        log.info("   - Actors: {}", actorDao.count());
        log.info("   - Comments: {}", commentDao.count());
        log.info("   - Movie Showtimes: {}", movieSaloonTimeDao.count());
    }
}
