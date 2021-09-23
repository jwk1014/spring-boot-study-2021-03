import http from './http';

export const getPostList = (params) =>
  http({
    url: `/v1/posts`,
    method: 'get',
    params,
  });

export const getPost = (id) =>
  http({
    url: `/v1/posts/${id}`,
    method: 'get',
  });

export const createPost = ({title, content}) =>
  http({
    url: `/v1/posts`,
    method: 'post',
    data: {title: title, content: content},
  });