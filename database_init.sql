-- CineVision Database Initialization Script for Neon DB
-- Run these INSERT statements to populate your database with dummy data

-- Categories Table
INSERT INTO category (category_name) VALUES 
('Action'),
('Drama'),
('Comedy'),
('Horror'),
('Sci-Fi'),
('Romance'),
('Thriller'),
('Adventure'),
('Animation'),
('Documentary');

-- Directors Table  
INSERT INTO director (director_name) VALUES
('Christopher Nolan'),
('Steven Spielberg'),
('Quentin Tarantino'),
('Martin Scorsese'),
('James Cameron'),
('Alfred Hitchcock'),
('Francis Ford Coppola'),
('Ridley Scott'),
('Tim Burton'),
('David Fincher');

-- Sample Movies Table
INSERT INTO movie (movie_name, description, duration, release_date, is_display, movie_trailer_url, category_id, director_id) VALUES
('Inception', 'A skilled thief is given a chance at redemption if he can successfully perform an inception.', 148, '2010-07-16', true, 'https://www.youtube.com/watch?v=YoHD9XEInc0', 1, 1),
('The Dark Knight', 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests.', 152, '2008-07-18', true, 'https://www.youtube.com/watch?v=EXeTwQWrcwY', 1, 1),
('Pulp Fiction', 'The lives of two mob hitmen, a boxer, a gangster and his wife intertwine in four tales of violence and redemption.', 154, '1994-10-14', true, 'https://www.youtube.com/watch?v=s7EdQ4FqbhY', 2, 3),
('Avatar', 'A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.', 162, '2009-12-18', false, 'https://www.youtube.com/watch?v=5PSNL1qE6VY', 5, 5),
('Goodfellas', 'The story of Henry Hill and his life in the mob, covering his relationship with his wife Karen Hill and his mob partners.', 146, '1990-09-21', true, 'https://www.youtube.com/watch?v=qo5jJpHtI40', 2, 4),
('Titanic', 'A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.', 194, '1997-12-19', true, 'https://www.youtube.com/watch?v=2e-eXJ6HgkQ', 6, 5),
('The Shawshank Redemption', 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', 142, '1994-09-23', true, 'https://www.youtube.com/watch?v=6hB3S9bIaco', 2, 4),
('Interstellar', 'A team of explorers travel through a wormhole in space in an attempt to ensure humanitys survival.', 169, '2014-11-07', false, 'https://www.youtube.com/watch?v=zSWdZVtXT7E', 5, 1),
('The Godfather', 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', 175, '1972-03-24', true, 'https://www.youtube.com/watch?v=sY1S34973zA', 2, 7),
('Jurassic Park', 'A pragmatic paleontologist visiting an almost complete theme park is tasked with protecting a couple of kids after a power failure causes the parks cloned dinosaurs to run loose.', 127, '1993-06-11', true, 'https://www.youtube.com/watch?v=lc0UehYemOA', 8, 2);

-- Sample Actors Table
INSERT INTO actor (actor_name, movie_movie_id) VALUES
-- Inception actors
('Leonardo DiCaprio', 1),
('Marion Cotillard', 1),
('Tom Hardy', 1),
('Ellen Page', 1),
('Ken Watanabe', 1),

-- The Dark Knight actors
('Christian Bale', 2),
('Heath Ledger', 2),
('Aaron Eckhart', 2),
('Maggie Gyllenhaal', 2),
('Gary Oldman', 2),

-- Pulp Fiction actors
('John Travolta', 3),
('Samuel L. Jackson', 3),
('Uma Thurman', 3),
('Bruce Willis', 3),
('Ving Rhames', 3),

-- Avatar actors
('Sam Worthington', 4),
('Zoe Saldana', 4),
('Sigourney Weaver', 4),
('Stephen Lang', 4),
('Michelle Rodriguez', 4),

-- Goodfellas actors
('Robert De Niro', 5),
('Ray Liotta', 5),
('Joe Pesci', 5),
('Lorraine Bracco', 5),
('Paul Sorvino', 5);

-- Sample Cities (for movie locations/theaters)
INSERT INTO city (city_name) VALUES
('New York'),
('Los Angeles'),
('Chicago'),
('Houston'),
('Phoenix'),
('Philadelphia'),
('San Antonio'),
('San Diego'),
('Dallas'),
('San Jose'),
('Austin'),
('Jacksonville'),
('Fort Worth'),
('Columbus'),
('Charlotte');

-- Sample Saloons (theaters/cinema halls)
INSERT INTO saloon (saloon_name) VALUES
('Hall A'),
('Hall B'),
('Hall C'),
('IMAX Theater'),
('Premium Hall'),
('VIP Theater'),
('3D Theater'),
('Standard Hall 1'),
('Standard Hall 2'),
('Standard Hall 3');

-- Note: User data should be added through the API endpoints for proper password encryption
-- The admin user will be created automatically by the DataInitializer class

-- Sample Comments (after movies are added)
INSERT INTO comment (comment_text, rating, user_email, movie_movie_id) VALUES
('Amazing movie! Christopher Nolan at his best.', 5, 'john.doe@example.com', 1),
('Mind-bending plot and excellent cinematography.', 5, 'jane.smith@example.com', 1),
('Heath Ledger''s performance as Joker is legendary.', 5, 'movie.lover@example.com', 2),
('One of the best superhero movies ever made.', 5, 'batman.fan@example.com', 2),
('Tarantino''s masterpiece with incredible dialogue.', 5, 'film.critic@example.com', 3),
('Visual effects in Avatar are groundbreaking.', 4, 'scifi.fan@example.com', 4),
('Classic gangster movie with great acting.', 5, 'classic.movies@example.com', 5);
