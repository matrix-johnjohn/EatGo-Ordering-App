import { Button } from "antd";

export const TableComplexButton = (status: number, onAction: () => void) => {
  if (status === 0) {
    return (
      <>
        <Button color="primary" variant="solid" onClick={onAction}>
          出餐
        </Button>
      </>
    );
  } else if (status === 2) {
    return (
      <>
        <Button color="cyan" variant="solid">
          已评论
        </Button>
      </>
    );
  } else if (status === 101) {
    return (
      <>
        <Button color="default" variant="link">
          已取消
        </Button>
      </>
    );
  } else {
    return (
      <>
        <Button color="purple" variant="solid">
          已出餐
        </Button>
      </>
    );
  }
};
