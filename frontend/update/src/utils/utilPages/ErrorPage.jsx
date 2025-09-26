import React from 'react'
import { Link } from 'react-router-dom'

export default function ErrorPage() {
  return (
    <div className='d-flex container justify-content-center align-items-center text-center' style={{height:"75vh"}}>
        
    <main>
        <h1>Sorry, we couldn't find the page you're looking for</h1>
        <h3>404 Page Not Found</h3>
        <div className='container mt-4'>
            <div className='col-sm-12 text-center'>
                <Link to="/">
                    <button className='btn btn-primary'>Return to Homepage <i className="fa-solid fa-house"></i></button>
                </Link> 
            </div>
        </div>
    </main>

</div>
  )
}
