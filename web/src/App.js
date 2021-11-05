import { lazy, Suspense } from 'react';
import { HashRouter, Route, Switch } from 'react-router-dom';
import SideBar from './components/sidebar';
import logo from './logo.svg';
import './App.css';
import SideBarComponent from './components/sidebar';

const IndexComponent = lazy(() => import('./components/index'));
const PostListComponent = lazy(() => import('./components/post/list'));

//const PostInfo = lazy(() => import('./components/post/info'));

function App() {
  return (
    <div className="App">
      <div id="wrapper">
          <HashRouter>
            <SideBarComponent />
            <div id="content-wrapper" class="d-flex flex-column">
              <Suspense fallback={<h1>Loading...</h1>}>
                <Switch>
                  <Route exact path="/" component={IndexComponent} />
                  <Route exact path="/posts" component={PostListComponent} />
                  <Route path="*" component={() => (<h1>404 Not Found</h1>)} />
                </Switch>
              </Suspense>
            </div>
          </HashRouter>
      </div>
    </div>
  );
}

export default App;
