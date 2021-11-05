import { useEffect, useRef, useState } from 'react';
import { useHistory, useLocation } from 'react-router-dom';
import queryString from 'query-string';
import { getPostList } from '../../apis/post';

const QueryInputName = {
  page: 'page',
  size: 'size'
};
//
//const PaginationComponent = ({ page, lastPage, onPage }) => {
//  const maxCount = 10;
//  const startPage = page - (page % maxCount) + (page % maxCount === 0 ? maxCount : 0) + 1;
//  const pageLength = Math.min(lastPage - startPage + 1, maxCount);
//  if (lastPage < 2) {
//    return <></>;
//  }
//  return (
//    <Pagination>
//      {page > maxCount && <Pagination.Prev onClick={() => onPage(startPage - 1)} />}
//      {Array.from({length: pageLength}, (_, i) => startPage + i).map((value, index) => (
//        <Pagination.Item
//          key={index}
//          className={page === value ? 'active' : ''}
//          onClick={() => onPage(startPage + index)}
//          >
//            {value}
//          </Pagination.Item>
//      ))}
//      {lastPage >= startPage + maxCount && <Pagination.Next onClick={() => onPage(startPage + maxCount)} />}
//    </Pagination>
//  )
//};

const PostListComponent = () => {
  const history = useHistory();
  const search = useLocation().search;
  const urlQuery = queryString.parse(search);
  
  const [queryInput, setQueryInput] = useState({
      ...(Object.fromEntries(Object.entries(QueryInputName).map(([k,v]) => [k, urlQuery[k] || '']))),
      [QueryInputName.page]: urlQuery[QueryInputName.page] || '1',
      [QueryInputName.size]: urlQuery[QueryInputName.size] || '20',
  });

  const page = queryInput.page || '1';
  const lastPage = useRef(1);

  const [postList, setPostList] = useState([]);

  useEffect(() => {
    console.log('call useEffect getPostList');
    getPostList(queryInput)
      .then(({ data }) => {
        lastPage.current = data.lastPage;
        setPostList(data.list);
      })
      .catch(error => {
        alert(`${error.response?.status} / ${error.response?.data} / ${error.message}`);
      });
  }, [search]);

  const submit = (page) => {
    const query = Object.entries({...queryInput, [QueryInputName.page]: page})
      .join('&');
    history.push(`/posts${query.length > 0 ? '?' : ''}${query}`);
  };

  return (
  <div id="content" style={{padding:'1rem'}}>
    { postList && postList.map( item =>
      (<div class="row" style={{'margin-top':'1rem', 'justify-content': 'center'}}>
            <div class="col-lg-6 mb-4">
              <div class="card shadow mb-4">
                  <div class="card-header py-3">
                      <h6 class="m-0 font-weight-bold text-primary">{item.title}</h6>
                  </div>
                  <div class="card-body">
                      <p>by {item.user.name}</p>
                  </div>
              </div>
            </div>
          </div>)
       )}
  </div>
  );
};

export default PostListComponent;