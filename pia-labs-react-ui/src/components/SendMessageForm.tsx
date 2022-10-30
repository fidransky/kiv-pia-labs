import { ActionFunctionArgs, Form } from "react-router-dom";
import { sendMessage } from "../services/RoomService";

type Props = {
}

export async function action({ request, params }: ActionFunctionArgs) {
    const formData = await request.formData();
    const text = formData.get('text')?.toString();

    if (text === undefined) return;

    return sendMessage(params.roomId, text);
}

export default function SendMessageForm(props: Props) {
    return (
        <Form method="post" action="message" className="mt-3">
            <div className="row g-3 align-items-center">
                <div className="col-auto">
                    <label htmlFor="messageText" className="col-form-label">
                        <strong>TOOD: john.doe:</strong>
                    </label>
                </div>
                <div className="col">
                    <input type="text" name="text" placeholder="Say something..." className="form-control" id="messageText" required autoFocus/>
                </div>
                <div className="col-auto">
                    <button type="submit" className="btn btn-primary">Send</button>
                </div>
            </div>
        </Form>
    );
}