import React, { useState } from 'react'
import { Formik, Form, Field } from "formik";
import * as yup from "yup";
import { ActorService } from '../../services/actorService';
import { useEffect } from 'react';
import { CityService } from '../../services/cityService';
import KaanKaplanSelect from '../../utils/customFormItems/KaanKaplanSelect';
import { CategoryService } from '../../services/categoryService';
import { DirectorService } from '../../services/directorService';
import KaanKaplanTextInput from '../../utils/customFormItems/KaanKaplanTextInput';
import KaanKaplanTextArea from '../../utils/customFormItems/KaanKaplanTextArea';
import { MovieService } from '../../services/movieService';
import { useNavigate } from 'react-router-dom';
import KaanKaplanCheckBox from '../../utils/customFormItems/KaanKaplanCheckBox';
import { useSelector } from 'react-redux';

export default function AddMoviePage() {

    const userFromRedux = useSelector(state => state.user.payload)

    const navigate = useNavigate()

    const actorService = new ActorService();
    const cityService = new CityService();
    const categoryService = new CategoryService();
    const directorService = new DirectorService();
    const movieService = new MovieService();

    const [actors, setActors] = useState([])
    const [cities, setCities] = useState([])
    const [categories, setCategories] = useState([])
    const [directors, setDirectors] = useState([])

    useEffect(() => {
      actorService.getall().then(result => setActors(result.data))
      cityService.getall().then(result => setCities(result.data))
      categoryService.getall().then(result => setCategories(result.data))
      directorService.getall().then(result => setDirectors(result.data))
    }, [])
    
    const initValues = {
        movieName: "",
        description: "",
        duration: "",
        releaseDate: "",
        trailerUrl: "",
        categoryId: "",
        directorId: "",
        directorName: "",
        isInVision: true
    }

    const validationSchema = yup.object({
        movieName: yup.string().required("Movie name is required"),
        description: yup.string().required("Description is required"),
        duration: yup.number().required("Duration is required").min(1, "Duration must be greater than 0"),
        releaseDate: yup.date().required("Release date is required"),
        trailerUrl: yup.string().url("Please enter a valid URL"),
        categoryId: yup.string().required("Please select a category")
    })


  return (
    <div>
        <div className='mt-5 pt-4 container-fluid'>
            <div className='row justify-content-center'>
                <div className='col-12 col-md-10 col-lg-8 col-xl-6'>
                    <div className='p-3 p-md-4'>
                        <h2 className='mt-4 text-center text-md-start'>Add Movie</h2>
                        <hr />

                        <h5 className='my-4 text-center text-md-start'>
                            Fill in the movie information completely and proceed to select the movie's actors.
                        </h5>

                        <Formik 
                            initialValues={initValues}
                            validationSchema={validationSchema}
                            onSubmit={(values) => {
                                console.log("Form submitted with values:", values);
                                values.userAccessToken = userFromRedux.token; // Change here

                                if(values.directorId === undefined || values.directorId === ""){
                                    console.log("Adding new director:", values.directorName);
                                    let director={
                                        directorName: values.directorName,
                                        token: userFromRedux.token
                                    }
                                    directorService.add(director).then(result => {
                                        console.log("Director added:", result.data);
                                        values.directorId = result.data.directorId
                                        movieService.addMovie(values).then(result => 
                                            {
                                                console.log("Movie added:", result.data);
                                                if(result.data != ""){
                                                    navigate("/addMovie/" + result.data.movieId)
                                                }
                                            }).catch(error => {
                                                console.error("Error adding movie:", error);
                                            });
                                    }).catch(error => {
                                        console.error("Error adding director:", error);
                                    })
                                } else {
                                    console.log("Using existing director:", values.directorId);
                                    movieService.addMovie(values).then(result => 
                                        { 
                                            console.log("Movie added:", result.data);
                                            if(result.data !== ""){
                                                navigate("/addMovie/" + result.data.movieId)
                                            }
                                        }).catch(error => {
                                            console.error("Error adding movie:", error);
                                        });
                                }
                            }}>

                            <Form>
                                <div className="row">
                                    <div className="col-12 col-md-6">
                                        <div className="form-floating mb-3">
                                            <KaanKaplanTextInput  type="text" name='movieName' className="form-control" id="floatingInput" placeholder="Movie Name" />
                                            <label htmlFor="floatingInput">Movie Name</label>
                                        </div>
                                    </div>
                                    <div className="col-12 col-md-6">
                                        <div className="form-floating mb-3">
                                            <KaanKaplanTextInput  name='duration' type="number" className="form-control" id="duration" placeholder="Duration" />
                                            <label htmlFor="duration">Movie Duration (minutes)</label>
                                        </div>
                                    </div>
                                </div>

                                <div className="form-floating mb-3">
                                    <KaanKaplanTextArea name='description' className="form-control" id="floatingPassword" placeholder="Summary" style={{minHeight: '120px'}} />
                                    <label htmlFor="floatingPassword">Movie Summary</label>
                                </div>

                                <div className="row">
                                    <div className="col-12 col-md-6">
                                        <div className="form-floating mb-3">
                                            <KaanKaplanTextInput name='releaseDate' type="date" className="form-control" id="releaseDate" placeholder="Release Date" />
                                            <label htmlFor="releaseDate">Release Date</label>
                                        </div>
                                    </div>
                                    <div className="col-12 col-md-6">
                                        <div className="mb-3">
                                            <label htmlFor="categoryId" className="form-label">Category</label>
                                            <KaanKaplanSelect
                                                id="categoryId"
                                                className="form-select form-select-lg"
                                                name="categoryId"
                                                placeholder="Select a category"
                                                options={categories.map(category => (
                                                    {key: category?.categoryId, value: category?.categoryName}
                                                ))}
                                            />
                                        </div>
                                    </div>
                                </div>
                                
                                <div className="form-floating mb-3">
                                    <KaanKaplanTextInput name='trailerUrl' type="text" className="form-control" id="trailerUrl" placeholder="Trailer URL" />
                                    <label htmlFor="trailerUrl">Trailer URL</label>
                                </div>

                                <div className="mb-3">
                                    <label htmlFor="directorId" className="form-label">Director</label>
                                    <KaanKaplanSelect
                                        id="directorId"
                                        className="form-select form-select-lg"
                                        name="directorId"
                                        placeholder="Select a director"
                                        options={directors.map(director => (
                                            {key: director?.directorId, value: director?.directorName}
                                        ))}
                                    />
                                </div>

                                <div className="alert alert-info mb-3">
                                    <small>If the director is not in the list above, please write it below.</small>
                                </div>

                                <div className="form-floating mb-3">
                                    <KaanKaplanTextInput name='directorName' type="text" className="form-control" id="directorName" placeholder="Director Name" />
                                    <label htmlFor="directorName">Director Name</label>
                                </div>

                                <div className="form-check mb-4 text-start">
                                    <KaanKaplanCheckBox name="isInVision" className="form-check-input" type="checkbox" id="isInVision" />
                                    <label className="form-check-label" htmlFor="isInVision">
                                        Is Movie Currently Showing?
                                    </label>
                                </div>
                              
                                {/* Add via file later */}
                                {/* <div className="input-group mb-3">
                                    <input type="file" className="form-control" id="image" />
                                </div> */}

                                <div className="d-grid gap-2 my-4 col-12 col-sm-8 col-md-6 mx-auto">
                                  <input
                                    type="submit"
                                    value="Next"
                                    className="btn btn-lg btn-primary"
                                  />
                                </div>
                            </Form>
                        </Formik>
                    </div>
                </div>
            </div>
        </div>
    </div>
  )
}
