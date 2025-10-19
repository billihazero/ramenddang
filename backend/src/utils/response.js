export const successRes = (res, data = [], message = '성공', total = null) => {
  res.status(200).json({
    status: true,
    messages: message,
    data,
    total: total ?? (Array.isArray(data) ? data.length : 1),
  });
};

export const errorRes = (
  res,
  message = '에러발생',
  statusCode = 500,
  data = [],
) => {
  res.status(statusCode).json({
    status: false,
    messages: message,
    data,
    total: 0,
  });
};
